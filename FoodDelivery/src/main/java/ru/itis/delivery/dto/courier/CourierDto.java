package ru.itis.delivery.dto.courier;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.delivery.models.enums.WorkState;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные о курьере")
public class CourierDto {

    @Schema(description = "Идентификатор курьера", example = "5")
    private Long id;

    @Schema(description = "Имя клиента", example = "Петр")
    private String nameOfCourier;

    @Schema(description = "Номер телефона клиента", example = "+79634567890")
    private String numberOfPhone;

    @Schema(description = "Рабочий статус клиента", example = "WORK/RELAX")
    private WorkState workState;

}
