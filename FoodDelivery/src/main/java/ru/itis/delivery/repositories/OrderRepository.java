package ru.itis.delivery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.delivery.models.Client;
import ru.itis.delivery.models.Courier;
import ru.itis.delivery.models.Order;
import ru.itis.delivery.models.enums.OrderState;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByState(OrderState state);

    List<Order> findByStateNot(OrderState state);

    List<Order> findAllByCourierAndState(Courier courier, OrderState orderState);

    List<Order> findAllByClientAndStateNot(Client client, OrderState orderState);

    List<Order> findAllByClientAndState(Client client, OrderState orderState);

}
