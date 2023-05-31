package ru.itis.delivery.dto.feedback;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Новый отзыв с данными")
public class RequestFeedbackForm {

    @Min(value = 1, message = "Minimum value is 1")
    @Max(value = 5, message = "Maximum value is 5")
    @NotNull
    @Schema(description = "Оценка, выставленная пользователем", example = "3")
    private Integer stars;

    @NotBlank
    @Schema(description = "Комментарий, оставленный пользователем", example = "Долгоо везли")
    private String commentary;

}
