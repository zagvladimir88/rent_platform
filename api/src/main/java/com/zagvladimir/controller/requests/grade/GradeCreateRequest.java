package com.zagvladimir.controller.requests.grade;

import com.zagvladimir.domain.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Data
public class GradeCreateRequest {

  @NotNull
  @Schema(defaultValue = "2", type = "Long" , description = "Item leased id")
  private long itemId;

  @NotNull
  @Schema(defaultValue = "3", type = "Long" , description = "The id of the user who rated")
  private long userId;

  @Schema(defaultValue = "Good", type = "number($double)" , description = "Description")
  private String description;

  @Schema(defaultValue = "5.0", type = "number(double)" , description = "Grade")
  @DecimalMin(value = "0.0")
  @DecimalMax(value = "5.0")
  private double grade;

  @Schema(defaultValue = "ACTIVE", type = "string" , description = "Status")
  private Status status = Status.ACTIVE;
}
