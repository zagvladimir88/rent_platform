package com.zagvladimir.domain;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude = {"itemCategory","items"})
@ToString(exclude = {"itemCategory"})
@Table(name = "sub_item_types")
public class SubItemType extends BaseEntity{

    @Column(name = "sub_category_name")
    private String subCategoryName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ItemCategory itemCategory;

    @OneToMany(mappedBy="subItemType", fetch = FetchType.EAGER)
    @JsonManagedReference(value="subItemType")
    Set<Item> items;
}
