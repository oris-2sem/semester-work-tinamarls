package ru.itis.delivery.mappers.general;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itis.delivery.dto.CartItemDto;
import ru.itis.delivery.dto.product.ProductDto;
import ru.itis.delivery.models.CartItem;
import ru.itis.delivery.models.Product;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartItemConverter {

    private final ProductConverter productConverter;

    public CartItemDto toDto(CartItem cartItem){

        Product product = cartItem.getProduct();
        ProductDto productDto = productConverter.toDto(product);

        return CartItemDto.builder()
                .count(cartItem.getCount())
                .product(productDto).build();

    }

    public List<CartItemDto> toDtoList(List<CartItem> cartItemList){
        return cartItemList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
