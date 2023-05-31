package ru.itis.delivery.services;

import ru.itis.delivery.dto.user.RequestRegistrationForm;
import ru.itis.delivery.models.Client;
import ru.itis.delivery.models.Courier;
import ru.itis.delivery.models.User;

public interface UserService {
    void saveClient(RequestRegistrationForm registrationForm);

    Client findClientByUsername(String username);

    User saveCourier(RequestRegistrationForm registrationForm);

    Courier findCourierByUsername(String username);
}
