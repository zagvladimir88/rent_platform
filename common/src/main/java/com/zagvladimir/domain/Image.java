package com.zagvladimir.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Data
@Entity
@EqualsAndHashCode(exclude = {"item"})
@Table(name = "images")
public class Image extends AuditingEntity{

    private String link;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @JsonBackReference
    private Item items;


}
