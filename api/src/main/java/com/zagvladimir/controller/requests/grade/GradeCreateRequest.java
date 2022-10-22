package com.zagvladimir.controller.requests.grade;

import com.zagvladimir.domain.enums.Status;
import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Data
public class GradeCreateRequest {

  private long itemLeasedId;

  private long userFromId;

  private long userToId;

  @NotNull
  private String description;

  @DecimalMin(value = "0.0")
  @DecimalMax(value = "5.0")
  private double grade;

  private Status status = Status.ACTIVE;
}
