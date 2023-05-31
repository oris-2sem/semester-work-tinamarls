package ru.itis.delivery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.delivery.dto.order.OrderDto;
import ru.itis.delivery.dto.product.ProductDto;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Информация о продуктах в заказе пользователя")
public class OrderProductDto {

    @Schema(description = "Идентификатор", example = "1")
    private Long id;

    @Schema(description = "Заказ")
    private OrderDto order;

    @Schema(description = "Продукт в заказе")
    private ProductDto product;

    @Schema(description = "Количество продукта в заказе", example = "5")
    private Integer count;
}
