package com.utn.UTNPhones.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.utn.UTNPhones.domain.Call;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CallDto {
    private Integer id;
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime start;
    private Integer duration;
    private Double total;
    private Integer idBill;
    private LineDto origin;
    private LineDto destination;

    public static CallDto from ( Call call) {
        return CallDto.builder()
                .id(call.getId())
                .start(call.getStart())
                .duration(call.getDuration())
                .total(call.getTotal())
                .idBill(call.getIdBill())
                .origin(LineDto.from(call.getOrigin()))
                .destination(LineDto.from(call.getDestination()))
                .build();
    }
}
