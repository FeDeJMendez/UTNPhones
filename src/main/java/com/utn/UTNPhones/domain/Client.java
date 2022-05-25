package com.utn.UTNPhones.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("CLIENT")
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "clients")
public class Client extends Person {
    @OneToOne
    @JoinColumn(name = "id_line ", unique = true)
    private Line line;
}
