package ru.itis.delivery.models;

import lombok.*;
import ru.itis.delivery.models.enums.OrderState;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "ordering")
public class Order {

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", nameOfClient='" + nameOfClient + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", fullPrice=" + fullPrice +
                ", state=" + state +
                ", address='" + address + '\'' +
                ", commentary='" + commentary + '\'' +
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    @Column(name = "name_recipient")
    private String nameOfClient;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "total_price")
    private Double fullPrice;

    @Enumerated(EnumType.STRING)
    private OrderState state;

    private String address;
    private String commentary;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "courier_id")
    private Courier courier;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts;

}
