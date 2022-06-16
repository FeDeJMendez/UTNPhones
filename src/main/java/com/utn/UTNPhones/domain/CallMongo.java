package com.utn.UTNPhones.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "calls")
public class CallMongo {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String originNumber;
    private String originCityName;
    private String destinationNumber;
    private String destinationCityName;
    private Double total;
    private Integer duration;
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private LocalDateTime starttime;
}
