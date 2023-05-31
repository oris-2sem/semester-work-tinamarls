package ru.itis.delivery.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.itis.delivery.dto.feedback.FeedbackDto;
import ru.itis.delivery.dto.feedback.RequestFeedbackForm;
import ru.itis.delivery.exceptions.NotFoundException;
import ru.itis.delivery.mappers.FeedbackMapper;
import ru.itis.delivery.models.Feedback;
import ru.itis.delivery.repositories.FeedbackRepository;
import ru.itis.delivery.services.FeedbackService;

import java.time.LocalDate;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@RequiredArgsConstructor
@Slf4j
public class FeedbackServiceImpl implements FeedbackService {

    FeedbackRepository feedbackRepository;

    FeedbackMapper feedbackMapper;

    @Override
    public List<FeedbackDto> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));

        return feedbackMapper.toDtoList(feedbacks);
    }

    @Override
    public void deleteById(Long id) {
        feedbackRepository.deleteById(id);
    }

    @Override
    public FeedbackDto updateFeedback(Long id, RequestFeedbackForm requestFeedbackForm) {
        Feedback feedback = getFeedbackOrThrow(id);

        feedback.setCommentary(requestFeedbackForm.getCommentary());
        feedback.setStars(requestFeedbackForm.getStars());

        return feedbackMapper.toDto(feedbackRepository.save(feedback));
    }

    @Override
    public FeedbackDto getFeedbackById(Long id) {
        return feedbackMapper.toDto(getFeedbackOrThrow(id));
    }

    @Override
    public FeedbackDto saveNewFeedback(RequestFeedbackForm requestFeedbackForm) {
        Feedback feedback = feedbackMapper.toFeedback(requestFeedbackForm);
        feedback.setDate(LocalDate.now());
        return feedbackMapper.toDto(feedbackRepository.save(feedback));
    }

    private Feedback getFeedbackOrThrow(Long id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Отзыв с идентификатором " + id + " не найден"));
    }
}
