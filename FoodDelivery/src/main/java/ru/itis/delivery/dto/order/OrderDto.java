package ru.itis.delivery.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.delivery.dto.client.ClientDto;
import ru.itis.delivery.dto.courier.CourierDto;
import ru.itis.delivery.models.enums.OrderState;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Информация о заказе")
public class OrderDto {
    @Schema(description = "Идентификатор отзыва", example = "9")
    private Long id;
    @Schema(description = "Дата и время оформления заказа", example = "2023-05-27 21:13:07")
    private LocalDateTime date;
    @Schema(description = "Имя получателя заказа", example = "Кристина")
    private String nameOfClient;
    @Schema(description = "Номер телефона получателя", example = "+79870379906")
    private String phoneNumber;
    @Schema(description = "Полная стоимость заказа(после вычета всех бонусов и скидок)", example = "1009")
    private Double fullPrice;
    @Schema(description = "Статус заказа", example = "COMPLETED")
    private OrderState state;
    @Schema(description = "Адрес доставки", example = "Деревня Универсиады, д.18, к.308")
    private String address;
    @Schema(description = "Комментарий к заказу", example = "Привезите заказ ориентировочно к 16:30")
    private String commentary;
    @Schema(description = "Клиент, оформивший заказ")
    private ClientDto client;
    @Schema(description = "Курьер, выполняющий заказ")
    private CourierDto courier;
}
