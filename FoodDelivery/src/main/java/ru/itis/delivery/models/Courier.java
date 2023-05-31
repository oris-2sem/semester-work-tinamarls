package ru.itis.delivery.models;

import lombok.*;
import ru.itis.delivery.models.enums.WorkState;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
@Entity
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String nameOfCourier;

    @Column(name = "phone_number")
    private String numberOfPhone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "work_state")
    @Enumerated(EnumType.STRING)
    private WorkState workState;

    @OneToMany(mappedBy = "courier")
    private List<Order> orders;


}
