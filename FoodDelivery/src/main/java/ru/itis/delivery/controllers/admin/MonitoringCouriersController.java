package ru.itis.delivery.controllers.admin;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.delivery.dto.courier.RequestCourierForm;
import ru.itis.delivery.models.Courier;
import ru.itis.delivery.services.CourierService;

import javax.validation.Valid;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/courier")
public class MonitoringCouriersController {

    CourierService courierService;

    @GetMapping
    public String getAdminCourierPage(Model model){
        model.addAttribute("requestCourierForm", new RequestCourierForm());
        return "admin/monitorCouriers";
    }

    @PostMapping
    public String addNewCourier(@ModelAttribute("requestCourierForm") @Valid RequestCourierForm requestCourierForm,
                                BindingResult bindingResult, Model model){

        if(!bindingResult.hasErrors()){
            Courier newCourier = courierService.saveCourier(requestCourierForm);

            model.addAttribute("successMessage",
                    "Курьер " + newCourier.getNameOfCourier() + " успешно зарегестрирован");
            model.addAttribute("requestCourierForm", new RequestCourierForm());
        }

        return "admin/monitorCouriers";

    }

}
