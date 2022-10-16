package com.zagvladimir.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "items")
@ToString(exclude = "images")
public class Item extends BaseEntity{

    @Column(name = "item_name")
    private String itemName;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "item_type_id")
    private SubItemType subItemType;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "item_location")
    private String itemLocation;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "price_per_hour")
    private double pricePerHour;

    @Column(name = "available")
    private Boolean available;

    @OneToMany(mappedBy = "item")
    @JsonManagedReference
    private Set<Image> images;
}
