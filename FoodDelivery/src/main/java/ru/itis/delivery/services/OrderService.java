package ru.itis.delivery.services;

import ru.itis.delivery.dto.order.OrderDto;
import ru.itis.delivery.dto.OrderProductDto;
import ru.itis.delivery.dto.order.RequestOrderForm;
import ru.itis.delivery.models.Client;
import ru.itis.delivery.models.Order;
import ru.itis.delivery.models.enums.OrderState;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Map<OrderDto, List<OrderProductDto>> findOrdersByStatus(OrderState inSearchCourier);

    void assignCourierById(String idOrder, String idCourier);

    Map<OrderDto, List<OrderProductDto>> findNotCompletedOrders();

    void changeOrderState(String idOrder, String state);

    Order addOrder(RequestOrderForm requestOrderForm, String username);

    void setOrderStateCompleted(Long orderId);

    Map<OrderDto, List<OrderProductDto>> findAllCurrentOrdersByClient(Client client);

    Map<OrderDto, List<OrderProductDto>> findAllCompletedtOrdersByClient(Client client);
}
