package ru.itis.delivery.models;

import lombok.*;
import ru.itis.delivery.models.enums.Role;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
@Entity
@Table(name = "user_account")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hash_password")
    private String hashPassword;
    private String login;

    @Enumerated(EnumType.STRING)
    private Role role;

}
