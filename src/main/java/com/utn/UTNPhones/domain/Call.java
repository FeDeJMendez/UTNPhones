package com.utn.UTNPhones.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "calls")
public class Call {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String origin;

    @Column
    private String destination;

    @Column
    private LocalDateTime start;

    @Column
    private Integer duration;

    @Column
    private Double total;

    @Column
    private String idBill;
}
