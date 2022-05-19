package com.utn.UTNPhones.domain;

import com.utn.UTNPhones.utils.URIInterface;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class Client implements URIInterface {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String lastname;

    @Column(unique=true)
    private Integer dni;

    @OneToOne
    @JoinColumn(name = "id_line ", unique = true)
    private Line line;
}
