package ru.itis.delivery.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Информация о позиции в меню")
public class ProductDto {
    @Schema(description = "Идентификатор позиции в меню", example = "1")
    private Long id;
    @Schema(description = "Название позиции", example = "Сырная")
    private String nameOfProduct;
    @Schema(description = "Цена", example = "599")
    private Double price;
    @Schema(description = "Категория", example = "Пицца")
    private String category;
    @Schema(description = "Описание позиции", example = "4 вида сыра")
    private String description;
    @Schema(description = "Калорийность", example = "289")
    private Integer calories;
    @Schema(description = "Фотография позиции, хранящаяся в системе", example = "28923c91-4c58-43cf-ae23-1557a76ac5bb_photo_2023-05-23_16-47-39.jpg")
    private String photoName;
//    @Schema(description = "", example = "28923c91-4c58-43cf-ae23-1557a76ac5bb_photo_2023-05-23_16-47-39.jpg")
//    private Long photoId;

}
