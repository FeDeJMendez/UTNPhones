package com.utn.UTNPhones.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.utn.UTNPhones.dto.CityDto;
import com.utn.UTNPhones.utils.URIInterface;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cities")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class City implements URIInterface {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String name;

    @Column(unique = true)
    private Integer prefix;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "province_id", nullable = false)
    private Province province;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "city")
    private List<Phoneline> phonelines;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "origin")
    private List<Rate> origins;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "destination")
    private List<Rate> destinations;

    public static City from (CityDto cityDto) {
        return City.builder()
//                .id(cityDto.getId())
                .name(cityDto.getName())
                .prefix(cityDto.getPrefix())
                .province(Province.builder()
                        .id(cityDto.getProvince().getId())
                        .build())
                .build();
    }
}
