package ru.itis.delivery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Информация об ошибке")
public class ExceptionDto {

    @Schema(description = "Сообщение об ошибке", example = "Продукт с таким идентификатором не найден")
    private String message;

    @Schema(description = "Код ошибки", example = "404")
    private int status;
}
