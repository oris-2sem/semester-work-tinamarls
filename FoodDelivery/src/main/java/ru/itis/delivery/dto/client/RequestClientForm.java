package ru.itis.delivery.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Новые данные о клиенте")
public class RequestClientForm {

    @Schema(description = "Имя клиента", example = "Людмила")
    private String nameOfClient;
    @Pattern(regexp = "\\+7\\d{10}", message = "{courier.phone.format}")
    @Schema(description = "Номер телефона клиента", example = "+79178016398")
    private String numberOfPhone;

    @Schema(description = "Дата рождения клиента, которую он может указать только один раз"
            , example = "2004-01-01")
    private String dateOfBirth;
}
