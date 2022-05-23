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
    private Integer idClient;
    private Integer totalCalls;
    private Double costPrice;
    private Double totalPrice;
    private LocalDate date;
    private LocalDate expiration;
    private Boolean paid;

    public static BillDto from (Bill bill) {
        return BillDto.builder()
                .id(bill.getId())
                .idClient(bill.getIdClient())
                .totalCalls(bill.getTotalCalls())
                .costPrice(bill.getCostPrice())
                .totalPrice(bill.getTotalPrice())
                .date(bill.getDate())
                .expiration(bill.getExpiration())
                .paid(bill.getPaid())
                .build();
    }
}
