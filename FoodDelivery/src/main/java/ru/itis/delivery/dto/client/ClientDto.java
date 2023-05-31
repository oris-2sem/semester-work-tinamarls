package ru.itis.delivery.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные о клиенте")
public class ClientDto {

    @Schema(description = "Идентификатор клиента", example = "1")
    private Long id;

    @Schema(description = "Имя клиенте", example = "Кристина")
    private String nameOfClient;

    @Schema(description = "Номер телефона клиенте", example = "+79870379906")
    private String numberOfPhone;

    @Schema(description = "Дата рождения клиента", example = "2003-09-11")
    private Date dateOfBirth;

    @Schema(description = "Количество бонусов клиента", example = "500")
    private Integer bonuses;

}
