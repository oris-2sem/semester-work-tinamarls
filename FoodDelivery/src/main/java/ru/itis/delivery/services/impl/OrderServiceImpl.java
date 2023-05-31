package ru.itis.delivery.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itis.delivery.dto.order.OrderDto;
import ru.itis.delivery.dto.OrderProductDto;
import ru.itis.delivery.dto.order.RequestOrderForm;
import ru.itis.delivery.exceptions.NotFoundException;
import ru.itis.delivery.mappers.OrderMapper;
import ru.itis.delivery.mappers.OrderProductMapper;
import ru.itis.delivery.mappers.general.OrderConverter;
import ru.itis.delivery.models.*;
import ru.itis.delivery.models.enums.OrderState;
import ru.itis.delivery.repositories.CourierRepository;
import ru.itis.delivery.repositories.OrderProductRepository;
import ru.itis.delivery.repositories.OrderRepository;
import ru.itis.delivery.services.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;
    OrderMapper orderMapper;
    OrderConverter orderConverter;
    OrderProductRepository orderProductRepository;
    OrderProductMapper orderProductMapper;
    CourierService courierService;
    ClientService clientService;
    UserService userService;
    CartService cartService;

    @Override
    public Map<OrderDto, List<OrderProductDto>> findOrdersByStatus(OrderState inSearchCourier) {

        Map<OrderDto, List<OrderProductDto>> fullOrders = new HashMap<>();

        List<Order> orders = orderRepository.findByState(inSearchCourier);

        for (Order order : orders){
            List<OrderProduct> orderProductList = orderProductRepository.findProductsByOrder(order);

            List<OrderProductDto> productsInOrder = orderProductList.stream()
                    .map(orderProductMapper::toDto)
                    .toList();
            fullOrders.put(orderMapper.toDto(order), productsInOrder);
        }

        return fullOrders;
    }

    @Override
    public void assignCourierById(String idOrder, String idCourier) {
        Order orderForUpdate = getOrderOrThrow(Long.valueOf(idOrder));
        Courier courier = courierService.findCourierById(Long.valueOf(idCourier));

        orderForUpdate.setState(OrderState.IN_DELIVERY);

        orderForUpdate.setCourier(courier);

        orderRepository.save(orderForUpdate);
    }

    @Override
    public void changeOrderState(String idOrder, String state) {
        Order order = getOrderOrThrow(Long.valueOf(idOrder));
        OrderState newState = OrderState.valueOf(state);
        order.setState(newState);

        if(newState == OrderState.IN_SEARCH_COURIER){
            order.setCourier(null);
        }

        orderRepository.save(order);
    }

    @Override
    public Order addOrder(RequestOrderForm requestOrderForm, String username) {
        Client client = userService.findClientByUsername(username);

        if(requestOrderForm.isUseBonuses()){
            clientService.writeOffBonuses(client);
        }

        clientService.addBonuses(client, requestOrderForm.getTotalPrice());

        Order order = orderConverter.toOrder(requestOrderForm);

        order.setDate(LocalDateTime.now());
        order.setClient(client);
        order.setState(OrderState.ACCEPTED);

        Order savedOrder = orderRepository.save(order);

        List<CartItem> productsInCart = cartService.findProductsInActiveCart(client);
        List<OrderProduct> orderProducts = fromCart(productsInCart, order);

        cartService.cleanCartByClient(client);

        savedOrder.setOrderProducts(orderProducts);

        return orderRepository.save(savedOrder);

    }

    @Override
    public void setOrderStateCompleted(Long orderId) {
        Order order = getOrderOrThrow(orderId);
        order.setState(OrderState.COMPLETED);
        orderRepository.save(order);
    }

    @Override
    public Map<OrderDto, List<OrderProductDto>> findNotCompletedOrders() {

        List<Order> orders = orderRepository.findByStateNot(OrderState.COMPLETED);

        return fromOrdersToMapDto(orders);
    }

    @Override
    public Map<OrderDto, List<OrderProductDto>> findAllCurrentOrdersByClient(Client client) {

        List<Order> orders = orderRepository.findAllByClientAndStateNot(client, OrderState.COMPLETED);

        return fromOrdersToMapDto(orders);
    }

    @Override
    public Map<OrderDto, List<OrderProductDto>> findAllCompletedtOrdersByClient(Client client) {
        List<Order> orders = orderRepository.findAllByClientAndState(client, OrderState.COMPLETED);

        return fromOrdersToMapDto(orders);
    }

    // метод, чтобы перенести товары из корзины в оформленный заказ
    private List<OrderProduct> fromCart(List<CartItem> productInCart, Order order){
        List<OrderProduct> orderProductList = new ArrayList<>();

        for(CartItem cartItem: productInCart){
            OrderProduct orderProduct = OrderProduct.builder()
                    .product(cartItem.getProduct())
                    .order(order)
                    .count(cartItem.getCount())
                    .build();
            orderProductList.add(orderProduct);
            orderProductRepository.save(orderProduct);
        }
        return orderProductList;
    }

    // метод для того, чтобы вернуть все заказы с продуктами, которые были заказаны
    private Map<OrderDto, List<OrderProductDto>> fromOrdersToMapDto(List<Order> orders){
        Map<OrderDto, List<OrderProductDto>> fullOrders = new HashMap<>();

        for (Order order : orders){
            List<OrderProduct> orderProductList = order.getOrderProducts();
            log.info(orderProductList.toString());
            List<OrderProductDto> productsInOrder = orderProductList.stream()
                    .map(orderProductMapper::toDto)
                    .toList();
            fullOrders.put(orderMapper.toDto(order), productsInOrder);
        }

        return fullOrders;
    }

    private Order getOrderOrThrow(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Заказ с идентификатором " + id + " не найден"));
    }

}
