package com.zagvladimir.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "items")
public class Item extends BaseEntity{

    @Column(name = "item_name")
    private String itemName;

    @ManyToOne
    @JsonBackReference(value = "subItemType")
    @JoinColumn(name = "item_type_id")
    private SubItemType subItemType;

    @ManyToOne
    @JsonBackReference(value="item-location")
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "item_location")
    private String itemLocation;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonBackReference(value="owner")
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "price_per_hour")
    private double pricePerHour;

    @Column(name = "available")
    private Boolean available;
}
