package com.zagvladimir.domain;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude = {"country","items","users"})
@Table(name = "locations")
@ToString(exclude = {"items","users"})
public class Location extends AuditingEntity{

    @Column(name = "postal_code")
    private String postalCode;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;


    @ManyToOne
    @JoinColumn(name = "country_id")
    @JsonManagedReference
    private Country country;


    @OneToMany(mappedBy="location", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Item> items;


    @OneToMany(mappedBy="location", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<User> users;

}
