package ru.itis.delivery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.delivery.models.Cart;
import ru.itis.delivery.models.Client;
import ru.itis.delivery.models.enums.CartState;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByClientAndCartState(Client client, CartState cartState);


}
