package ru.itis.delivery.services;

import ru.itis.delivery.dto.feedback.FeedbackDto;
import ru.itis.delivery.dto.feedback.RequestFeedbackForm;

import java.util.List;

public interface FeedbackService {
    List<FeedbackDto> getAllFeedbacks();

    void deleteById(Long id);

    FeedbackDto updateFeedback(Long id, RequestFeedbackForm requestFeedbackForm);

    FeedbackDto getFeedbackById(Long id);

    FeedbackDto saveNewFeedback(RequestFeedbackForm requestFeedbackForm);
}
