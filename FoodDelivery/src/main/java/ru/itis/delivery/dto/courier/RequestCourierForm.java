package ru.itis.delivery.dto.courier;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.delivery.validation.constraints.EmailUnique;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные о курьере для регистрации в системе")
public class RequestCourierForm {

    @EmailUnique
    @NotBlank(message = "{courier.login.isEmpty}")
    @Schema(description = "Выдаваемая курьеру почта", example = "cour5@mail.ru")
    private String login;

    @NotBlank(message = "{courier.password.isEmpty}")
    @Schema(description = "Пароль, выдаваемый курьеру для входа в систему", example = "password")
    private String password;

    @NotBlank(message = "{courier.personal.isEmpty}")
    @Schema(description = "Имя курьера", example = "Павел")
    private String nameOfCourier;

    @NotBlank(message = "{courier.personal.isEmpty}")
    @Pattern(regexp = "\\+7\\d{10}", message = "{courier.phone.format}")
    @Schema(description = "Номер телефона курьера", example = "+79178016397")
    private String numberOfPhone;

}
