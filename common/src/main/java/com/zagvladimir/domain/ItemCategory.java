package com.zagvladimir.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "item_categories")
public class ItemCategory extends BaseEntity{

    @Column(name = "category_name")
    private String categoryName;

}
