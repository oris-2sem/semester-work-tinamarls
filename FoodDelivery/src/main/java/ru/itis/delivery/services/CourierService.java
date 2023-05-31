package ru.itis.delivery.services;

import ru.itis.delivery.dto.courier.CourierDto;
import ru.itis.delivery.dto.courier.RequestCourierForm;
import ru.itis.delivery.dto.order.OrderDto;
import ru.itis.delivery.models.Courier;

import java.util.List;

public interface CourierService {
    List<CourierDto> findWorkingCouriers();

    Courier saveCourier(RequestCourierForm requestCourierForm);

    boolean findWorkStateByUsername(String username);

    boolean changeState(String state, String username);

    List<OrderDto> findAllCurrentOrders(String username);

    Courier findCourierById(Long id);
}
