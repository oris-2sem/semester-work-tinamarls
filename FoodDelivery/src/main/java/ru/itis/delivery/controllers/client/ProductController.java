package ru.itis.delivery.controllers.client;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.delivery.dto.product.ProductDto;
import ru.itis.delivery.services.ProductService;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Controller
@RequestMapping("/product")
public class ProductController {

    ProductService productService;

    @GetMapping("/{product-id}")
    public String getProductPage(@PathVariable("product-id") Long productId, Model model){
        ProductDto productDto = productService.getProductById(productId);
        model.addAttribute("product", productDto);

        return "client/product";
    }

}
