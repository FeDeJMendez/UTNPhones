package com.utn.UTNPhones.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Builder
//@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("BACKOFFICE")
@EqualsAndHashCode(callSuper = true)
@Entity
//@Table(name = "backoffices")
public class Backoffice extends Person {

}
