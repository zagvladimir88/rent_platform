package com.zagvladimir.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "categories")
public class Category extends AuditingEntity{

    @Column(name = "category_name")
    private String categoryName;

}
