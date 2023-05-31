package ru.itis.delivery.mappers;

import org.mapstruct.Mapper;
import ru.itis.delivery.dto.feedback.FeedbackDto;
import ru.itis.delivery.dto.feedback.RequestFeedbackForm;
import ru.itis.delivery.models.Feedback;

import java.util.List;

@Mapper
public interface FeedbackMapper {

    List<FeedbackDto> toDtoList(List<Feedback> feedbacks);

    Feedback toFeedback(RequestFeedbackForm requestFeedbackForm);

    FeedbackDto toDto(Feedback feedback);

}
