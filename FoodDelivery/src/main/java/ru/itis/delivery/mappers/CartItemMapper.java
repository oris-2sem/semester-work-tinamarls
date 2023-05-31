package ru.itis.delivery.mappers;

import org.mapstruct.Mapper;
import ru.itis.delivery.dto.CartItemDto;
import ru.itis.delivery.models.CartItem;

import java.util.List;

@Mapper
public interface CartItemMapper {
    List<CartItemDto> toDtoList(List<CartItem> cartItemList);
}
