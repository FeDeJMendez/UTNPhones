package com.utn.UTNPhones.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.utn.UTNPhones.utils.URIInterface;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name = "persons")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type"/*, visible = true*/)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Client.class, name = "CLIENT"),
        @JsonSubTypes.Type(value = Backoffice.class, name = "BACKOFFICE")
})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Person implements URIInterface {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String lastname;

    @Column(unique=true)
    private Integer dni;


}
