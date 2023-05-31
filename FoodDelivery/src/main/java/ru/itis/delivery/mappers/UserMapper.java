package ru.itis.delivery.mappers;

import org.mapstruct.Mapper;
import ru.itis.delivery.dto.courier.RequestCourierForm;
import ru.itis.delivery.dto.user.RequestRegistrationForm;
import ru.itis.delivery.models.User;

@Mapper
public interface UserMapper {
    User toUser(RequestRegistrationForm form);

    RequestRegistrationForm toRegistrationForm(RequestCourierForm courierForm);
}
