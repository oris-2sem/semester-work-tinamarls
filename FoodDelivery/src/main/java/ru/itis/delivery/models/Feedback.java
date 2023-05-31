package ru.itis.delivery.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "count_of_stars", length = 1)
    private Integer stars;

    @Column(length = 1024)
    private String commentary;

    private LocalDate date;

}
