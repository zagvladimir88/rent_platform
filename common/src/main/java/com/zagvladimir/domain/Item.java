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

    @Column(name = "item_type_id")
    private int itemTypeId;

    @Column(name = "location_id")
    private int locationId;

    @Column(name = "item_location")
    private String itemLocation;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonBackReference
    private User owner;

    @Column(name = "price_per_hour")
    private double pricePerHour;

    @Column(name = "available")
    private Boolean available;
}
