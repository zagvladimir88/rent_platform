package com.zagvladimir.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(exclude = {"user","item"})
@Table(name = "grades")
public class Grade extends AuditingEntity{

    @Column(name = "grade")
    private double grade;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @JsonBackReference
    private Item item;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
}
