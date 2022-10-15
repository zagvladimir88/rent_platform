package com.zagvladimir.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@ToString(exclude = {"country"})
public class Location extends BaseEntity{

    @Column(name = "postal_code")
    private String postalCode;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;


    @ManyToOne
    @JoinColumn(name = "country_id")
    @JsonBackReference
    private Country country;


    @OneToMany(mappedBy="location", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Item> items;


    @OneToMany(mappedBy="location", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<User> users;

}
