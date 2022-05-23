package com.utn.UTNPhones.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.utn.UTNPhones.utils.URIInterface;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bills")
public class Bill implements URIInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer idClient;

    @Column
    private Integer totalCalls;

    @Column
    private Double costPrice;

    @Column
    private Double totalPrice;

    @Column
    private LocalDate date;

    @Column
    private LocalDate expiration;

    @Column
    private Boolean paid;
}
