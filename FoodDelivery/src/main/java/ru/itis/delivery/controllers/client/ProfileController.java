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
import ru.itis.delivery.dto.OrderProductDto;
import ru.itis.delivery.dto.client.ClientDto;
import ru.itis.delivery.dto.client.RequestClientForm;
import ru.itis.delivery.dto.order.OrderDto;
import ru.itis.delivery.dto.product.RequestProductForm;
import ru.itis.delivery.models.Client;
import ru.itis.delivery.models.Order;
import ru.itis.delivery.services.ClientService;
import ru.itis.delivery.services.OrderService;
import ru.itis.delivery.services.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Controller
@RequestMapping("/profile")
public class ProfileController {

    ClientService clientService;
    OrderService orderService;
    UserService userService;

    @GetMapping
    public String getProfilePage(Model model, Authentication authentication){

        UserDetails user = (UserDetails) authentication.getPrincipal();
        ClientDto clientDto = clientService.getClientDtoByUsername(user.getUsername());

        Client client = userService.findClientByUsername(user.getUsername());

        Map<OrderDto, List<OrderProductDto>> currentOrders = orderService.findAllCurrentOrdersByClient(client);
        Map<OrderDto, List<OrderProductDto>> completedOrders = orderService.findAllCompletedtOrdersByClient(client);

        model.addAttribute("currentOrders", currentOrders);
        model.addAttribute("completedOrders", completedOrders);

        model.addAttribute("requestClientForm", new RequestClientForm());
        model.addAttribute("client", clientDto);

        return "client/profile";
    }

    @PutMapping
    public String updateClient(@ModelAttribute("requestClientForm") @Valid RequestClientForm requestClientForm,
                               @RequestParam("clientId") String idClient){

        clientService.updateClientById(Long.valueOf(idClient), requestClientForm);

        return "redirect:/profile";
    }

}
