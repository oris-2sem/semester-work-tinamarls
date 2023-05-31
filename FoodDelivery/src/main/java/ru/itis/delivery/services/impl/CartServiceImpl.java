package ru.itis.delivery.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.itis.delivery.dto.CartItemDto;
import ru.itis.delivery.dto.product.ProductDto;
import ru.itis.delivery.exceptions.NotFoundException;
import ru.itis.delivery.mappers.general.CartItemConverter;
import ru.itis.delivery.models.*;
import ru.itis.delivery.models.enums.CartState;
import ru.itis.delivery.repositories.*;
import ru.itis.delivery.repositories.custom.CustomCartRepository;
import ru.itis.delivery.services.CartService;
import ru.itis.delivery.services.ProductService;
import ru.itis.delivery.services.UserService;
import ru.itis.delivery.services.apiCurrency.CurrencyService;
import ru.itis.delivery.services.clazz.SessionBag;

import java.util.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    CartRepository cartRepository;

    CartItemRepository cartItemRepository;

    ProductService productService;

    CustomCartRepository customCartRepository;

    CartItemConverter cartItemConverter;

    UserService userService;

    CurrencyService currencyService;


    @Override
    public void addProductToCart(Long productId, String username) {

        Product product = productService.findProductById(productId);

        Client client = userService.findClientByUsername(username);

        Cart nowCart;
        Optional<Cart> cartOptional = cartRepository.findByClientAndCartState(client, CartState.ACTIVE);

        if(cartOptional.isPresent()){
            nowCart = cartOptional.get();
        } else {
            Cart cart = Cart.builder().client(client).build();
            nowCart = cartRepository.save(cart);
        }

        Optional<CartItem> cartItemOptional = cartItemRepository.findByCartAndProduct(nowCart, product);

        CartItem cartItem;
        if(cartItemOptional.isPresent()){
            cartItem = cartItemOptional.get();
            cartItem.setCount(cartItem.getCount() + 1);
        } else {
            cartItem = CartItem.builder()
                    .cart(nowCart)
                    .count(1)
                    .product(product)
                    .build();
        }
        cartItemRepository.save(cartItem);
    }

    @Override
    public Integer getCountOfProductInClientBag(String username) {

        Client client = userService.findClientByUsername(username);

        return customCartRepository.getCountOfProductsInCartByClient(client);
    }

    @Override
    public void mergeCartFromSession(SessionBag sessionBag, String username) {
        Client client = userService.findClientByUsername(username);

        Optional<Cart> cartOptional = cartRepository.findByClientAndCartState(client, CartState.ACTIVE);

        if(cartOptional.isPresent()){
            Cart cart = cartOptional.get();

            for (Map.Entry<Product, Integer> entry : sessionBag.getBag().entrySet()) {
                Product product = entry.getKey();
                Integer count = entry.getValue();

                Optional<CartItem> cartItemOptional = cartItemRepository.findByCartAndProduct(cart, product);

                if(cartItemOptional.isPresent()){
                    CartItem needCartItem = cartItemOptional.get();
                    needCartItem.setCount(needCartItem.getCount() + count);
                    cartItemRepository.save(needCartItem);
                } else {
                    CartItem cartItem = CartItem.builder()
                            .count(count).product(product).cart(cart).build();
                    cartItemRepository.save(cartItem);
                }
            }
        } else {
            Cart cart = Cart.builder()
                    .cartState(CartState.ACTIVE)
                    .client(client).build();
            Cart savesCart = cartRepository.save(cart);

            for (Map.Entry<Product, Integer> entry : sessionBag.getBag().entrySet()) {
                CartItem cartItem = CartItem.builder()
                        .product(entry.getKey())
                        .count(entry.getValue())
                        .cart(savesCart).build();
                cartItemRepository.save(cartItem);
            }
        }
    }

    @Override
    public List<CartItemDto> findAllProductInCart(String username) {
        Client client = userService.findClientByUsername(username);
        Optional<Cart> cart = cartRepository.findByClientAndCartState(client, CartState.ACTIVE);
        if(cart.isPresent()){
            List<CartItem> cartItemListSorted = cartItemRepository.findAllByCart(cart.get(), Sort.by(Sort.Direction.ASC, "id"));
            return cartItemConverter.toDtoList(cartItemListSorted);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public void deleteOneProductFromCart(Long productId, String username) {
        Product product = productService.findProductById(productId);

        Client client = userService.findClientByUsername(username);

        Cart nowCart = findActiveCartByClient(client);

        Optional<CartItem> cartItemOptional = cartItemRepository.findByCartAndProduct(nowCart, product);
        if(cartItemOptional.isPresent()){
            CartItem cartItemForUpdate = cartItemOptional.get();
            if(cartItemForUpdate.getCount() > 1){
                cartItemForUpdate.setCount(cartItemForUpdate.getCount() - 1);
                cartItemRepository.save(cartItemForUpdate);
            }
        }
    }

    @Override
    public void removeFullProduct(Long productId, String username) {

        Product product = productService.findProductById(productId);

        Client client = userService.findClientByUsername(username);

        Cart nowCart = findActiveCartByClient(client);

        Optional<CartItem> cartItemOptional = cartItemRepository.findByCartAndProduct(nowCart, product);
        if(cartItemOptional.isPresent()){
            CartItem cartItemForUpdate = cartItemOptional.get();
            cartItemRepository.delete(cartItemForUpdate);
        }
    }

    @Override
    public Double getTotalPriceOfCartByBonuses(String username, boolean useBonuses) {

        Client client = userService.findClientByUsername(username);

        Cart cart = findActiveCartByClient(client);

        List<CartItem> cartItemList = cart.getCartItems();

        double totalPrice = cartItemList.stream()
                .mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getCount())
                .sum();

        if(useBonuses){
            double withUseBonuses = totalPrice - client.getBonuses();
            if(withUseBonuses >= 0){
                return withUseBonuses;
            } else {
                return (double) 0;
            }
        } else {
            return totalPrice;
        }

    }

    @Override
    public boolean hasProductsInCarts(String username) {

        Client client = userService.findClientByUsername(username);
        Cart cart = findActiveCartByClient(client);

        return cart.getCartItems().size() > 0;
    }

    @Override
    public void cleanCartByClient(Client client) {

        Cart cart = findActiveCartByClient(client);

        cart.setCartState(CartState.COMPLETED);
        cartRepository.save(cart);

        Cart newCart = Cart.builder().client(client).cartState(CartState.ACTIVE).build();
        cartRepository.save(newCart);
    }

    @Override
    public List<CartItem> findProductsInActiveCart(Client client) {
        Optional<Cart> cart = cartRepository.findByClientAndCartState(client, CartState.ACTIVE);
        if(cart.isPresent()){
            return cart.get().getCartItems();
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<CartItemDto> changeCurrencyInCart(List<CartItemDto> cartItemDtoList, String currency) {
        for (CartItemDto cartItemDto: cartItemDtoList){
            ProductDto productDto = cartItemDto.getProduct();
            productDto.setPrice(currencyService.convertCurrency(productDto.getPrice(), currency));
        }
        return cartItemDtoList;
    }

    private Cart findActiveCartByClient(Client client){

        Optional<Cart> cartOptional = cartRepository.findByClientAndCartState(client, CartState.ACTIVE);

        if(cartOptional.isPresent()){
            return cartOptional.get();
        } else {
            log.error("Нет Cart с статусом - " + CartState.ACTIVE + " у клиента - " + client);
            throw new NotFoundException("Не найдена текущая корзину у клиента");
        }
    }
}
