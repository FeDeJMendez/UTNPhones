package com.utn.UTNPhones.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.utn.UTNPhones.utils.URIInterface;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "rates")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rate implements URIInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Double price;

    @Column
    private LocalTime starttime;

    @Column
    private LocalTime endtime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "origin_city_id", nullable = false)
    private City origin;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination_city_id", nullable = false)
    private City destination;
}
