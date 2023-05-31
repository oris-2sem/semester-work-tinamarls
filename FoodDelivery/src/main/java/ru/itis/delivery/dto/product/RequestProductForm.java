package ru.itis.delivery.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.delivery.validation.constraints.PhotoConstraint;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Информация о новой позиции в меню")
public class RequestProductForm {
    @NotBlank(message = "{newProduct.field.isEmpty}")
    @Schema(description = "Название позиции", example = "Мясная")
    private String nameOfProduct;
    @NotNull(message = "{newProduct.field.isEmpty}")
    @Schema(description = "Цена", example = "490")
    private Integer price;
    @NotBlank(message = "{newProduct.field.isEmpty}")
    @Schema(description = "Категория", example = "Пицца")
    private String category;
    @NotBlank(message = "{newProduct.field.isEmpty}")
    @Schema(description = "Описание позиции", example = "Цыпленок, ветчина, пикантная пепперони, острая чоризо")
    private String description;
    @NotNull(message = "{newProduct.field.isEmpty}")
    @Schema(description = "Калорийность", example = "289")
    private Integer calories;
    @PhotoConstraint
    @Schema(description = "Фотография новой позиции")
    private MultipartFile photo;
}
