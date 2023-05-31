package ru.itis.delivery.mappers.general;

import org.springframework.stereotype.Component;
import ru.itis.delivery.dto.order.RequestOrderForm;
import ru.itis.delivery.models.Order;

@Component
public class OrderConverter {

    public Order toOrder(RequestOrderForm orderForm){
        return Order.builder()
                .nameOfClient(orderForm.getNameOfClient())
                .phoneNumber(orderForm.getPhoneNumber())
                .commentary(orderForm.getCommentary())
                .address(orderForm.getAddress())
                .fullPrice(orderForm.getTotalPrice())
                .build();
    }

}
