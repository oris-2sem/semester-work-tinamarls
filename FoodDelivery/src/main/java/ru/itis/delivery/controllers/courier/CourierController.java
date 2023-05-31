package ru.itis.delivery.controllers.courier;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.delivery.dto.order.OrderDto;
import ru.itis.delivery.services.CourierService;
import ru.itis.delivery.services.OrderService;

import java.util.List;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Controller
@RequestMapping("/courier")
public class CourierController {

    CourierService courierService;

    OrderService orderService;

    @GetMapping
    public String getCourierPage(Authentication authentication, Model model,
                                 @ModelAttribute("message") String message){

        UserDetails user = (UserDetails) authentication.getPrincipal();
        boolean isWorkedCourier = courierService.findWorkStateByUsername(user.getUsername());
        model.addAttribute("working", isWorkedCourier);

        if(isWorkedCourier){
            List<OrderDto> allOrders = courierService.findAllCurrentOrders(user.getUsername());
            model.addAttribute("allOrders", allOrders);
            if (message != null){
                model.addAttribute("canNotEnd", message);
            }
        }

        return "courier/workPage";
    }

    @PostMapping("/change-state")
    public String changeState(@RequestParam("work") String state, Authentication authentication,
                              RedirectAttributes redirectAttributes){

        UserDetails user = (UserDetails) authentication.getPrincipal();
        boolean isUpdate = courierService.changeState(state, user.getUsername());

        if(!isUpdate){
            redirectAttributes.addAttribute("message",
                    "Ты не можешь закончить смену, пока не выполнишь все заказы");
        }

        return "redirect:/courier";
    }

    @PostMapping("/done-order")
    public String doneOrder(@RequestParam("orderId") String orderId){
        if(orderId != null){
            orderService.setOrderStateCompleted(Long.valueOf(orderId));
        }
        return "redirect:/courier";
    }
}
