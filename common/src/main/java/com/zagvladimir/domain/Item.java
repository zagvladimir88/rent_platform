package com.zagvladimir.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude = {"images","grades","subCategory"})
@Table(name = "items")
@ToString(exclude = {"images","grades","subCategory"})
public class Item extends AuditingEntity{

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "description")
    private String description;

    @Column(name = "price_per_day")
    private double pricePerDay;

    @Column(name = "available")
    private Boolean available;

    @Column(name = "brand")
    private String brand;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "sub_category_id")
    private SubCategory subCategory;

    @OneToMany(mappedBy = "items")
    @JsonManagedReference
    private Set<Image> images;

    @OneToMany(mappedBy = "item")
    @JsonManagedReference
    private Set<Grade> grades;
}
