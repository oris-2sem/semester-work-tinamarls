package ru.itis.delivery.mappers;

import org.mapstruct.Mapper;
import ru.itis.delivery.dto.order.OrderDto;
import ru.itis.delivery.models.Order;

import java.util.List;

@Mapper
public interface OrderMapper {

    Order toOrder(OrderDto orderDto);

    OrderDto toDto(Order order);

    List<OrderDto> toDtoList(List<Order> orders);

}
