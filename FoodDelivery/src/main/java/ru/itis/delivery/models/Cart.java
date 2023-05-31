package ru.itis.delivery.models;

import lombok.*;
import ru.itis.delivery.models.enums.CartState;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private CartState cartState;

}
