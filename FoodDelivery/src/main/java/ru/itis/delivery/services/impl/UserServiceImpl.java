package ru.itis.delivery.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.delivery.dto.user.RequestRegistrationForm;
import ru.itis.delivery.exceptions.NotFoundException;
import ru.itis.delivery.mappers.UserMapper;
import ru.itis.delivery.models.Client;
import ru.itis.delivery.models.Courier;
import ru.itis.delivery.models.Product;
import ru.itis.delivery.models.User;
import ru.itis.delivery.models.enums.Role;
import ru.itis.delivery.repositories.ClientRepository;
import ru.itis.delivery.repositories.CourierRepository;
import ru.itis.delivery.repositories.UserRepository;
import ru.itis.delivery.services.UserService;

import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    PasswordEncoder passwordEncoder;

    UserMapper userMapper;

    UserRepository userRepository;

    ClientRepository clientRepository;

    CourierRepository courierRepository;

    @Override
    public void saveClient(RequestRegistrationForm registrationDto) {

        User user = userMapper.toUser(registrationDto);
        user.setRole(Role.CLIENT);

        String hashPassword = passwordEncoder.encode(registrationDto.getPassword());
        user.setHashPassword(hashPassword);

        User newUser = userRepository.save(user);
        Client newClient = Client.builder().user(newUser).build();
        clientRepository.save(newClient);
    }

    public Client findClientByUsername(String username){

        User user = getUserOrThrow(username);

        Optional<Client> clientOptional = clientRepository.findByUser(user);

        if(clientOptional.isPresent()){
            return clientOptional.get();
        } else {
            log.error("Не найден клиент с почтой" + username);
            throw new NotFoundException("Клиент с почтой " + username + " не найден");
        }
    }

    @Override
    public User saveCourier(RequestRegistrationForm registrationForm) {
        User user = userMapper.toUser(registrationForm);
        user.setRole(Role.COURIER);

        String hashPassword = passwordEncoder.encode(registrationForm.getPassword());
        user.setHashPassword(hashPassword);

        log.warn(user.toString());

        return userRepository.save(user);
    }

    @Override
    public Courier findCourierByUsername(String username) {

        User user = getUserOrThrow(username);

        Optional<Courier> courierOptional = courierRepository.findByUser(user);

        if(courierOptional.isPresent()){
            return courierOptional.get();
        } else {
            log.error("Не найден курьер с почтой" + username);
            throw new NotFoundException("Курьер с почтой " + username + " не найден");
        }

    }

    private User getUserOrThrow(String username) {
        return userRepository.findByLogin(username)
                .orElseThrow(() -> new NotFoundException("Пользователь с почтой " + username + " не найден"));
    }
}
