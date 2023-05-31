package ru.itis.delivery.controllers.feedback;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.delivery.dto.feedback.FeedbackDto;
import ru.itis.delivery.dto.feedback.RequestFeedbackForm;
import ru.itis.delivery.services.FeedbackService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RestController
public class FeedbackController implements FeedbackAPI{

    FeedbackService feedbackService;

    @Override
    public ResponseEntity<List<FeedbackDto>> getAllFeedbacks() {
        List<FeedbackDto> feedbacks = feedbackService.getAllFeedbacks();
        log.info(feedbacks + "FEEDBACK");

        return ResponseEntity.ok(feedbacks);
    }

    @Override
    public ResponseEntity<FeedbackDto> addNewFeedback(RequestFeedbackForm requestFeedbackForm) {
        FeedbackDto feedbackDto = feedbackService.saveNewFeedback(requestFeedbackForm);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(feedbackDto);
    }

    @Override
    public ResponseEntity<FeedbackDto> getFeedback(Long id) {
        FeedbackDto feedbackDto = feedbackService.getFeedbackById(id);
        return ResponseEntity.ok(feedbackDto);
    }

    @Override
    public ResponseEntity<FeedbackDto> updateFeedback(Long id, RequestFeedbackForm requestFeedbackForm) {
        FeedbackDto feedbackDto = feedbackService.updateFeedback(id, requestFeedbackForm);
        return ResponseEntity.accepted().body(feedbackDto);
    }

    @Override
    public ResponseEntity<?> deleteFeedback(Long id) {
        feedbackService.deleteById(id);
        return ResponseEntity.accepted().build();
    }
}
