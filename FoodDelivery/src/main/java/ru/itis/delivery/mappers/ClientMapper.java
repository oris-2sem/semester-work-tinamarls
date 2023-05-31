package ru.itis.delivery.mappers;

import org.mapstruct.Mapper;
import ru.itis.delivery.dto.client.ClientDto;
import ru.itis.delivery.dto.client.RequestClientForm;
import ru.itis.delivery.models.Client;

@Mapper
public interface ClientMapper {

    ClientDto toDto(Client client);

}
