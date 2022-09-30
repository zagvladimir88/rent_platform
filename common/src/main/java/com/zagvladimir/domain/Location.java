package com.zagvladimir.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude = {"country","items","users"})
@Table(name = "locations")
@ToString(exclude = "country")
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
    @JsonBackReference
    private Set<Item> items;

    @OneToMany(mappedBy="location", fetch = FetchType.EAGER)
    @JsonBackReference
    private Set<User> users;

}
