package ru.itis.delivery.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itis.delivery.dto.CartItemDto;
import ru.itis.delivery.exceptions.NotFoundException;
import ru.itis.delivery.mappers.general.CartItemConverter;
import ru.itis.delivery.models.CartItem;
import ru.itis.delivery.models.Product;
import ru.itis.delivery.repositories.ProductRepository;
import ru.itis.delivery.services.CartAnonymousService;
import ru.itis.delivery.services.clazz.SessionBag;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@RequiredArgsConstructor
@Slf4j
public class CartAnonymousServiceImpl implements CartAnonymousService {

    HttpSession session;

    ProductRepository productRepository;

    CartItemConverter cartItemConverter;

    @Override
    public void addProductToCart(Long productId) {

        SessionBag sessionBag = (SessionBag) session.getAttribute("bag");

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Позиция меню с идентификатором <" + productId + "> не найдена"));

        if(sessionBag != null){
            sessionBag.addOnePositionOfProduct(product);
            session.setAttribute("bag", sessionBag);
        } else {
            SessionBag newBag = new SessionBag();
            newBag.addOnePositionOfProduct(product);
            session.setAttribute("bag", newBag);
        }
    }

    @Override
    public Integer getCountOfProductInBag() {
        SessionBag sessionBag = (SessionBag) session.getAttribute("bag");

        if(sessionBag == null){
            return 0;
        } else {
            return sessionBag.getCountProductInBag();
        }
    }

    @Override
    public List<CartItemDto> findAllProductInCart() {
        SessionBag sessionBag = (SessionBag) session.getAttribute("bag");

        List<CartItem> cartItemList = new ArrayList<>();

        if(sessionBag != null){
            for(Map.Entry<Product, Integer> entry: sessionBag.getBag().entrySet()){
                Product product = entry.getKey();
                Integer count = entry.getValue();

                CartItem cartItem = CartItem.builder().product(product)
                        .count(count).build();
                cartItemList.add(cartItem);

            }
        }
        return cartItemConverter.toDtoList(cartItemList);

    }

    @Override
    public void deleteOneProductFromCart(Long productId) {

        SessionBag sessionBag = (SessionBag) session.getAttribute("bag");

        Product product = findProductById(productId);

        sessionBag.deleteOnePositionOfProduct(product);

    }

    @Override
    public void removeFullProduct(Long productId) {
        SessionBag sessionBag = (SessionBag) session.getAttribute("bag");

        Product product = findProductById(productId);

        sessionBag.deleteProduct(product);
    }

    private Product findProductById(Long productId){
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Позиция меню с идентификатором " + productId + " не найдена"));
    }
}
