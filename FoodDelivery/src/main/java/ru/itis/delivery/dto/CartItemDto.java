package ru.itis.delivery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.delivery.dto.product.ProductDto;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Информация о содержании корзины пользователя")
public class CartItemDto {

    @Schema(description = "Идентификатор", example = "1")
    private Long id;

    @Schema(description = "Продукт в корзине")
    private ProductDto product;

    @Schema(description = "Количество продукта в корзине", example = "1")
    private Integer count;
}
