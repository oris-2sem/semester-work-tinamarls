package ru.itis.delivery.controllers.client;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.delivery.dto.product.ProductDto;
import ru.itis.delivery.services.CartAnonymousService;
import ru.itis.delivery.services.CartService;
import ru.itis.delivery.services.ProductService;

import java.util.List;
import java.util.Map;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class MenuController {

    ProductService productService;

    CartAnonymousService cartAnonymousService;

    CartService cartService;

    @GetMapping
    public String getMainPage(Model model){

        Map<String, List<ProductDto>> allProductsByCategories = productService.getAllProductsByCategories();

        model.addAttribute("productsWithCategory", allProductsByCategories);
        model.addAttribute("allProducts", productService.findAllActive());

        return "client/main";
    }

    @GetMapping("/cart-count")
    public ResponseEntity<Integer> getCountOfProductInCart(Authentication authentication){
        if(authentication != null && isClient(authentication)){
            UserDetails user = (UserDetails) authentication.getPrincipal();
            Integer count = cartService.getCountOfProductInClientBag(user.getUsername());
            return ResponseEntity.ok(count);
        } else {
            return ResponseEntity.ok(cartAnonymousService.getCountOfProductInBag());
        }
    }

    @GetMapping("/feedback")
    public String getFeedbackPage(){
        return "client/feedback";
    }

    private boolean isClient(Authentication authentication){
        return authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("")
                .equals("CLIENT");
    }

}
