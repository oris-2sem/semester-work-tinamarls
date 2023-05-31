package ru.itis.delivery.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itis.delivery.dto.courier.CourierDto;
import ru.itis.delivery.dto.courier.RequestCourierForm;
import ru.itis.delivery.dto.order.OrderDto;
import ru.itis.delivery.exceptions.NotFoundException;
import ru.itis.delivery.mappers.CourierMapper;
import ru.itis.delivery.mappers.OrderMapper;
import ru.itis.delivery.mappers.UserMapper;
import ru.itis.delivery.models.Courier;
import ru.itis.delivery.models.Order;
import ru.itis.delivery.models.User;
import ru.itis.delivery.models.enums.OrderState;
import ru.itis.delivery.models.enums.WorkState;
import ru.itis.delivery.repositories.CourierRepository;
import ru.itis.delivery.repositories.OrderRepository;
import ru.itis.delivery.services.CourierService;
import ru.itis.delivery.services.UserService;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@RequiredArgsConstructor
@Slf4j
public class CourierServiceImpl implements CourierService {

    CourierRepository courierRepository;

    CourierMapper courierMapper;

    UserService userService;

    OrderRepository orderRepository;

    OrderMapper orderMapper;

    UserMapper userMapper;

    @Override
    public List<CourierDto> findWorkingCouriers() {
        List<Courier> couriers = courierRepository.findByWorkState(WorkState.WORKED);
        return courierMapper.toDtoList(couriers);
    }

    @Override
    public Courier saveCourier(RequestCourierForm requestCourierForm) {

        User user = userService.saveCourier(userMapper.toRegistrationForm(requestCourierForm));

        Courier courier = Courier.builder()
                .nameOfCourier(requestCourierForm.getNameOfCourier())
                .numberOfPhone(requestCourierForm.getNumberOfPhone())
                .user(user)
                .workState(WorkState.RELAX)
                .build();
        return courierRepository.save(courier);

    }

    @Override
    public boolean findWorkStateByUsername(String username) {
        Courier courier = userService.findCourierByUsername(username);
        return courier.getWorkState().equals(WorkState.WORKED);
    }

    @Override
    public boolean changeState(String state, String username) {
        Courier courier = userService.findCourierByUsername(username);

        WorkState workState = WorkState.valueOf(state);

        if(workState.equals(WorkState.RELAX)){

            if(orderRepository.findAllByCourierAndState(courier, OrderState.IN_DELIVERY).size() == 0){
                courier.setWorkState(workState);
                courierRepository.save(courier);
                return true;
            } else {
                return false;
            }
        } else {
            courier.setWorkState(workState);
            courierRepository.save(courier);
            return true;
        }

    }

    @Override
    public List<OrderDto> findAllCurrentOrders(String username) {
        Courier courier = userService.findCourierByUsername(username);
        List<Order> orders = orderRepository.findAllByCourierAndState(courier, OrderState.IN_DELIVERY);

        return orderMapper.toDtoList(orders);
    }

    @Override
    public Courier findCourierById(Long id) {
        return getCourierOrThrow(id);
    }

    private Courier getCourierOrThrow(Long id) {
        return courierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Курьер с идентификатором " + id + " не найден"));
    }


}
