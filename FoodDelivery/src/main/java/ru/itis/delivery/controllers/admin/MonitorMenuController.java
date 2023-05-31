package ru.itis.delivery.controllers.admin;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.delivery.dto.product.ProductDto;
import ru.itis.delivery.services.ProductService;

import java.util.List;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/menu")
public class MonitorMenuController {

    ProductService productService;

    @GetMapping
    public String getMenuPage(Model model){
        List<ProductDto> products = productService.findAllActive();
        model.addAttribute("products", products);
        return "admin/menu";
    }

}
