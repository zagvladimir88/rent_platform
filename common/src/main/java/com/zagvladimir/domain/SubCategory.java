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
@Table(name = "sub_categories")
public class SubCategory extends AuditingEntity{

    @Column(name = "sub_category_name")
    private String subCategoryName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy= "subCategory", fetch = FetchType.EAGER)
    @JsonManagedReference
    Set<Item> items;
}
