package com.zagvladimir.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude = {"locationSet"})
@Table(name = "countries")
@ToString(exclude = "locationSet")
public class Country extends BaseEntity{

    @Column(name = "country_name")
    private String countryName;

    @OneToMany(mappedBy="country", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Location> locationSet;
}
