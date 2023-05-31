package ru.itis.delivery.services;

import ru.itis.delivery.dto.client.ClientDto;
import ru.itis.delivery.dto.client.RequestClientForm;
import ru.itis.delivery.models.Client;

public interface ClientService {
    Integer findCountOfBonusesByUsername(String username);

    void writeOffBonuses(Client client);

    void addBonuses(Client client, Double totalPrice);

    ClientDto getClientDtoByUsername(String username);

    void updateClientById(Long valueOf, RequestClientForm requestClientForm);
}
