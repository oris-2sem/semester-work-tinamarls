package ru.itis.delivery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.delivery.models.Courier;
import ru.itis.delivery.models.User;
import ru.itis.delivery.models.enums.WorkState;

import java.util.List;
import java.util.Optional;

public interface CourierRepository extends JpaRepository<Courier, Long> {

    List<Courier> findByWorkState(WorkState state);

    Optional<Courier> findByUser(User user);
}
