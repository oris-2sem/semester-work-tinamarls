package ru.itis.delivery.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.delivery.validation.constraints.EmailUnique;

import javax.validation.constraints.NotBlank;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Информация о пользователе для регистрации")
public class RequestRegistrationForm {
    @NotBlank(message = "{user.login.isEmpty}")
    @EmailUnique
    @Schema(description = "Почта", example = "user@mail.ru")
    private String login;

    @NotBlank(message = "{user.password.isEmpty}")
    @Schema(description = "Пароль", example = "password")
    private String password;
}
