package com.zagvladimir.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "items")
public class Item extends BaseEntity{

    @Column(name = "item_name")
    private String itemName;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "item_type_id")
    private SubItemType subItemType;

    @ManyToOne
    @JsonManagedReference
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
}
