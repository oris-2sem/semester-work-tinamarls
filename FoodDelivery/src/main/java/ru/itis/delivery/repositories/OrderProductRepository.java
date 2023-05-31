package ru.itis.delivery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.delivery.models.Order;
import ru.itis.delivery.models.OrderProduct;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    List<OrderProduct> findProductsByOrder(Order order);

}

