package com.utn.UTNPhones.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.utn.UTNPhones.domain.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name ="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column
    String username;

    @JsonIgnore
    @Column
    String password;

    @Column(name = "rol")
    @Enumerated(EnumType.STRING)
    Rol rol;

    @Column
    Integer person_id;

    public User(String lastname, String pass, Rol roleClient, Integer person_id) {
        this.username = lastname;
        this.password = pass;
        this.rol = roleClient;
        this.person_id = person_id;
    }
}
