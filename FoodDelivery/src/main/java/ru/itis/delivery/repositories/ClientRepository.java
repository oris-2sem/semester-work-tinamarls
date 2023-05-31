package ru.itis.delivery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.delivery.models.Client;
import ru.itis.delivery.models.User;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByUser(User user);

}
