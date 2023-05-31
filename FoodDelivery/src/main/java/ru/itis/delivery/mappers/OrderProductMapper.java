package ru.itis.delivery.mappers;

import org.mapstruct.Mapper;
import ru.itis.delivery.dto.OrderProductDto;
import ru.itis.delivery.models.OrderProduct;

@Mapper
public interface OrderProductMapper {

    OrderProductDto toDto(OrderProduct orderProduct);
}
