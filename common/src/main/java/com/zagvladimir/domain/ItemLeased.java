package com.zagvladimir.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "items_leased")
public class ItemLeased extends AuditingEntity {

  @Column(name = "item_id")
  private long itemId;

  @JoinColumn(name = "renter_id")
  @ManyToOne
  @JsonBackReference
  private User renter;

  @Column(name = "time_from")
  private Timestamp timeFrom;

  @Column(name = "time_to")
  private Timestamp timeTo;

  @Column(name = "price_per_hour")
  private double pricePerHour;

  @Column private double discount;

  @Column private double fee;

  @Column(name = "price_total")
  private double priceTotal;

  @Column(name = "rentier_grade_description")
  private String rentierGradeDescription;

  @Column(name = "renter_grade_description")
  private String renterGradeDescription;
}
