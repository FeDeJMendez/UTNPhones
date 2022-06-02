package com.utn.UTNPhones.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.utn.UTNPhones.domain.Bill;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BillDto {
    private Integer id;
    private Integer dni;
    private String number;
    private Integer totalcalls;
    private Double costprice;
    private Double totalprice;
    private LocalDate datecreation;
    private LocalDate expiration;
    private Boolean paid;

    public static BillDto from (Bill bill) {
        return BillDto.builder()
                .id(bill.getId())
                .dni(bill.getDni())
                .number(bill.getNumber())
                .totalcalls(bill.getTotalcalls())
                .costprice(bill.getCostprice())
                .totalprice(bill.getTotalprice())
                .datecreation(bill.getDatecreation())
                .expiration(bill.getExpiration())
                .paid(bill.getPaid())
                .build();
    }
}
