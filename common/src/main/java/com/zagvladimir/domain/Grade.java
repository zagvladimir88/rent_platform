package com.zagvladimir.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(exclude = {"userFrom","userTo","itemLeased"})
@Table(name = "grades")
public class Grade extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "item_leased_id")
    @JsonBackReference
    private ItemLeased itemLeased;

    @ManyToOne
    @JoinColumn(name = "user_from_id")
    @JsonBackReference
    private User userFrom;

    @ManyToOne
    @JoinColumn(name = "user_to_id")
    @JsonBackReference
    private User userTo;

    @Column(name = "grade")
    private double grade;

    @Column(name = "description")
    private String description;

}
