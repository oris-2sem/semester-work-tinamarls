package ru.itis.delivery.services;

import ru.itis.delivery.dto.CartItemDto;
import ru.itis.delivery.models.CartItem;
import ru.itis.delivery.models.Client;
import ru.itis.delivery.services.clazz.SessionBag;

import java.util.List;

public interface CartService {
    void addProductToCart(Long productId, String username);

    Integer getCountOfProductInClientBag(String username);

    void mergeCartFromSession(SessionBag sessionBag, String username);

    List<CartItemDto> findAllProductInCart(String username);

    void deleteOneProductFromCart(Long productId, String username);

    void removeFullProduct(Long productId, String username);

    Double getTotalPriceOfCartByBonuses(String username, boolean useBonuses);

    boolean hasProductsInCarts(String username);

    void cleanCartByClient(Client client);

    List<CartItem> findProductsInActiveCart(Client client);

    List<CartItemDto> changeCurrencyInCart(List<CartItemDto> cartItemDtoList, String currency);
}
