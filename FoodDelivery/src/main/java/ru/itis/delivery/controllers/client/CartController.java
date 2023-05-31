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
import ru.itis.delivery.dto.CartItemDto;
import ru.itis.delivery.services.CartAnonymousService;
import ru.itis.delivery.services.CartService;
import ru.itis.delivery.services.ClientService;

import java.util.List;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Controller
@RequestMapping("/bag")
public class CartController {

    CartService cartService;
    CartAnonymousService cartAnonymousService;

    ClientService clientService;

    @GetMapping
    public String getCartPage(Model model, Authentication authentication){

        if(authentication != null && isClient(authentication)){
            UserDetails user = (UserDetails) authentication.getPrincipal();
            Integer bonuses = clientService.findCountOfBonusesByUsername(user.getUsername());
            model.addAttribute("bonuses", bonuses);
        } else {
            model.addAttribute("bonuses", 0);
        }

        return "client/bag";
    }

    @GetMapping("/cart-products")
    @ResponseBody
    public List<CartItemDto> getAllProductsInCart(Authentication authentication,
                                                  @RequestParam(value = "currency", defaultValue = "RUB") String currency){

        List<CartItemDto> cartItemDto;

        if(authentication != null && isClient(authentication)){
            UserDetails user = (UserDetails) authentication.getPrincipal();
            cartItemDto = cartService.findAllProductInCart(user.getUsername());
        } else {
            cartItemDto = cartAnonymousService.findAllProductInCart();
        }

        if(!currency.equals("RUB")){
            return cartService.changeCurrencyInCart(cartItemDto, currency);
        }

        return cartItemDto;

    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<?> addToCart(@RequestBody Long productId, Authentication authentication){

        if(authentication != null && isClient(authentication)){
            UserDetails user = (UserDetails) authentication.getPrincipal();
            cartService.addProductToCart(productId, user.getUsername());
        } else {
            cartAnonymousService.addProductToCart(productId);
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete-from-cart")
    public ResponseEntity<?> deleteFromCart(@RequestBody Long productId, Authentication authentication){

        if(authentication != null && isClient(authentication)){
            UserDetails user = (UserDetails) authentication.getPrincipal();
            cartService.deleteOneProductFromCart(productId, user.getUsername());
        } else {
            cartAnonymousService.deleteOneProductFromCart(productId);
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove")
    public ResponseEntity<?> removeCartItemFromCart(@RequestBody Long productId, Authentication authentication){

        if(authentication != null && isClient(authentication)){
            UserDetails user = (UserDetails) authentication.getPrincipal();
            cartService.removeFullProduct(productId, user.getUsername());
        } else {
            cartAnonymousService.removeFullProduct(productId);
        }

        return ResponseEntity.ok().build();
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
