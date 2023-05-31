package ru.itis.delivery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.delivery.models.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
