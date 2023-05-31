package ru.itis.delivery.controllers.admin;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.delivery.dto.courier.CourierDto;
import ru.itis.delivery.dto.order.OrderDto;
import ru.itis.delivery.dto.OrderProductDto;
import ru.itis.delivery.services.CourierService;
import ru.itis.delivery.services.OrderService;

import java.util.List;
import java.util.Map;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class MonitoringOrdersController {

    OrderService orderService;

    CourierService courierService;

    @GetMapping
    public String getMainAdminPage(Model model){

        Map<OrderDto, List<OrderProductDto>> allOrders =  orderService.findNotCompletedOrders();
        model.addAttribute("allOrders", allOrders);

        List<CourierDto> workingCouriers = courierService.findWorkingCouriers();
        model.addAttribute("couriers", workingCouriers);

        return "admin/monitorOrders";
    }

    @PutMapping("/assign-courier")
    public String assignCourierInOrder(@RequestParam("idCourier") String idCourier,
                                    @RequestParam("idOrder") String idOrder){

        orderService.assignCourierById(idOrder, idCourier);
        return "redirect:/admin";
    }

    @PutMapping("/change-state")
    public String changeStateOrder(@RequestParam("selectStatus") String state,
                                 @RequestParam("idOrder") String idOrder){

        orderService.changeOrderState(idOrder, state);
        return "redirect:/admin";
    }

}
