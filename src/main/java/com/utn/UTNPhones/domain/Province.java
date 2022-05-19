package com.utn.UTNPhones.domain;

import com.utn.UTNPhones.utils.URIInterface;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="provinces")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Province implements URIInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "province")
    private List<City> cities;
}
