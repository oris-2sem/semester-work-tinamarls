package ru.itis.delivery.controllers.client;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itis.delivery.dto.order.RequestOrderForm;
import ru.itis.delivery.models.Order;
import ru.itis.delivery.services.CartService;
import ru.itis.delivery.services.OrderService;

import javax.validation.Valid;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Controller
@RequestMapping("/order")
public class OrderController {

    CartService cartService;

    OrderService orderService;

    @GetMapping
    public String getOrderPage(@RequestParam(value = "useBonuses", required = false) boolean useBonuses,
                               Model model, Authentication authentication){

        UserDetails user = (UserDetails) authentication.getPrincipal();

        boolean hasProductsInCart = cartService.hasProductsInCarts(user.getUsername());
        if(hasProductsInCart){
            Double totalPriceOfCart = cartService.getTotalPriceOfCartByBonuses(user.getUsername(), useBonuses);
            model.addAttribute("requestOrderForm", RequestOrderForm.builder()
                    .totalPrice(totalPriceOfCart).useBonuses(useBonuses).build());
            return "client/order";
        } else {
            return "redirect:/bag";
        }
    }

    @PostMapping
    public String addNewOrder(@ModelAttribute("requestOrderForm") @Valid RequestOrderForm requestOrderForm,
                              BindingResult bindingResult, Authentication authentication){

        if(bindingResult.hasErrors()){
            return "client/order";
        }

        UserDetails user = (UserDetails) authentication.getPrincipal();

        orderService.addOrder(requestOrderForm, user.getUsername());

        return "redirect:/profile";

//        Order order = orderService.addOrder(requestOrderForm, user.getUsername());
//
//        if(order != null){
//            return "redirect:/profile";
//        } else {
//            return "redirect:/order";
//        }
    }


}
