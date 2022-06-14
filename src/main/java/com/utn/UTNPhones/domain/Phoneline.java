package com.utn.UTNPhones.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.utn.UTNPhones.dto.ClientDto;
import com.utn.UTNPhones.dto.PhonelineDto;
import com.utn.UTNPhones.utils.URIInterface;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "phonelines")
public class Phoneline implements URIInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 10, unique=true)
    private String number;

    @Column
    private Boolean status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "origin")
    private List<Call> origins;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "destination")
    private List<Call> destinations;

    public static Phoneline from (PhonelineDto phonelineDto) {
        /*City city = City.builder()
                .id(phonelineDto.getCity().getId())
                .name(phonelineDto.getCity().getName())
                .prefix(phonelineDto.getCity().getPrefix())
                *//*.province(Province.builder()
                        .id(phonelineDto.getCity().getProvince().getId())
                        .build())*//*
                .build();*/
        return Phoneline.builder()
//                .id(phonelineDto.getId())
                .number(phonelineDto.getNumber())
//                .status(phonelineDto.getStatus())
//                .city(city)
                .build();
    }

}
