package ru.itis.delivery.controllers.feedback;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.delivery.dto.ExceptionDto;
import ru.itis.delivery.dto.feedback.FeedbackDto;
import ru.itis.delivery.dto.feedback.RequestFeedbackForm;

import javax.validation.Valid;
import java.util.List;

@Tags(value = {
        @Tag(name = "feedbacks")
})
@RequestMapping("/feedback")
public interface FeedbackAPI {

    @Operation(summary = "Получение всех отзывов на доставку еды")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Страница с отзывами и обратной связью",
                content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FeedbackDto.class))
                })
    })
    @GetMapping("/all")
    ResponseEntity<List<FeedbackDto>> getAllFeedbacks();

    @Operation(summary = "Добавление нового отзыва")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Добавление нового отзыва",
                content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FeedbackDto.class))
                })
    })
    @PostMapping
    ResponseEntity<FeedbackDto> addNewFeedback(@Valid @RequestBody RequestFeedbackForm requestFeedbackForm);

    @Operation(summary = "Получение отзыва")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информацией о конкретном отзыве",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = FeedbackDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Сведение об ошибке",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))
                    })
    })
    @GetMapping("/{feedback-id}")
    ResponseEntity<FeedbackDto> getFeedback(@Parameter(description = "Идентификатор отзыва", example = "1")
                                          @PathVariable("feedback-id") Long id);

    @Operation(summary = "Обновление отзыва")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Обновленный отзыв",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = FeedbackDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Сведение об ошибке",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))
                    })
    })
    @PutMapping("/{feedback-id}")
    ResponseEntity<FeedbackDto> updateFeedback(
            @Parameter(description = "Идентификатор отзыва", example = "1") @PathVariable("feedback-id") Long id,
            @RequestBody RequestFeedbackForm requestFeedbackForm);


    @Operation(summary = "Удаление отзыва")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Отзыв удален"),
            @ApiResponse(responseCode = "404", description = "Сведение об ошибке",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))
                    })
    })
    @DeleteMapping ("/{feedback-id}")
    ResponseEntity<?> deleteFeedback(
            @Parameter(description = "Идентификатор отзыва", example = "1") @PathVariable("feedback-id") Long id);

}
