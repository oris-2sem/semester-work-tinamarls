package ru.itis.delivery.services;

import ru.itis.delivery.dto.CartItemDto;

import java.util.List;

public interface CartAnonymousService {
    void addProductToCart(Long productId);

    Integer getCountOfProductInBag();

    List<CartItemDto> findAllProductInCart();

    void deleteOneProductFromCart(Long productId);

    void removeFullProduct(Long productId);
}
