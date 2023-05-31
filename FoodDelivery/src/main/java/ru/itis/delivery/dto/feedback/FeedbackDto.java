package ru.itis.delivery.dto.feedback;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные о отзыве")
public class FeedbackDto {

    @Schema(description = "Оценка", example = "5")
    private Integer stars;

    @Schema(description = "Комментарий", example = "Все супер, спасибо")
    private String commentary;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Дата отправления отзыва", example = "2023-05-30")
    private LocalDate date;

}
