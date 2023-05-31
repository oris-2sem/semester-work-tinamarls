package ru.itis.delivery.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itis.delivery.dto.client.ClientDto;
import ru.itis.delivery.dto.client.RequestClientForm;
import ru.itis.delivery.exceptions.NotFoundException;
import ru.itis.delivery.mappers.ClientMapper;
import ru.itis.delivery.models.Client;
import ru.itis.delivery.models.Courier;
import ru.itis.delivery.repositories.ClientRepository;
import ru.itis.delivery.services.ClientService;
import ru.itis.delivery.services.UserService;

import java.sql.Date;
import java.util.Objects;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

    ClientRepository clientRepository;

    UserService userService;

    ClientMapper clientMapper;


    @Override
    public Integer findCountOfBonusesByUsername(String username) {
        Client client = userService.findClientByUsername(username);

        return client.getBonuses();
    }

    @Override
    public void writeOffBonuses(Client client) {
        client.setBonuses(0);
        clientRepository.save(client);
    }

    @Override
    public void addBonuses(Client client, Double totalPrice) {
        client.setBonuses((int) (client.getBonuses() + totalPrice * 0.1));
        clientRepository.save(client);
    }

    @Override
    public ClientDto getClientDtoByUsername(String username) {
        return clientMapper.toDto(userService.findClientByUsername(username));
    }

    @Override
    public void updateClientById(Long id, RequestClientForm requestClientForm) {
        Client client = getClientOrThrow(id);

        if(Objects.nonNull(requestClientForm.getNameOfClient())
                && !requestClientForm.getNameOfClient().isEmpty()){
            client.setNameOfClient(requestClientForm.getNameOfClient());
        }

        if(Objects.nonNull(requestClientForm.getNumberOfPhone())
                && !requestClientForm.getNumberOfPhone().isEmpty()){
            client.setNumberOfPhone(requestClientForm.getNumberOfPhone());
        }

        if(Objects.nonNull(requestClientForm.getDateOfBirth())
                && !requestClientForm.getDateOfBirth().isEmpty()){
            client.setDateOfBirth(Date.valueOf(requestClientForm.getDateOfBirth()));
        }

        clientRepository.save(client);
    }

    private Client getClientOrThrow(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Клиент с идентификатором " + id + " не найден"));
    }
}
