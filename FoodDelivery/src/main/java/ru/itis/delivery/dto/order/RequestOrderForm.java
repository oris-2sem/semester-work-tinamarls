package ru.itis.delivery.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для оформления заказа")
public class RequestOrderForm {
    @NotBlank(message = "{order.nameOfClient,isEmpty}")
    @Schema(description = "Имя получателя заказа", example = "Кристина")
    private String nameOfClient;
    @NotBlank(message = "{order.phoneNumber,isEmpty}")
    @Pattern(regexp = "\\+7\\d{10}", message = "{courier.phone.format}")
    @Schema(description = "Номер телефона получателя", example = "+79870379906")
    private String phoneNumber;
    @NotBlank(message = "{order.address,isEmpty}")
    @Schema(description = "Адрес доставки", example = "Деревня Универсиады, д.18, к.308")
    private String address;
    @Schema(description = "Комментарий к заказу", example = "Привезите заказ ориентировочно к 16:30")
    private String commentary;

    @Schema(description = "Информация, используются ли бонусы при оплате")
    private boolean useBonuses;
    @NotNull
    @Schema(description = "Полная стоимость заказа(после вычета всех бонусов и скидок)", example = "1009")
    private Double totalPrice;
}
