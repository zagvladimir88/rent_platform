package com.zagvladimir.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(exclude = {"itemCategory"})
@ToString(exclude = {"itemCategory"})
@Table(name = "sub_item_types")
public class SubItemType extends BaseEntity{

    @Column(name = "sub_category_name")
    private String subCategoryName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private ItemCategory itemCategory;
}
