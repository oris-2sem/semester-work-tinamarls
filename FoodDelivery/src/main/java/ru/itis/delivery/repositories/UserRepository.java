package ru.itis.delivery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.delivery.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);

    boolean existsByLogin(String email);

}
