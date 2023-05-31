package ru.itis.delivery.mappers;

import org.mapstruct.Mapper;
import ru.itis.delivery.dto.courier.CourierDto;
import ru.itis.delivery.models.Courier;

import java.util.List;

@Mapper
public interface CourierMapper {

    Courier toCourier(CourierDto courierDto);

    CourierDto toDto(Courier courier);

    List<CourierDto> toDtoList(List<Courier> couriers);

}
