package com.zagvladimir.controller.requests.grade;

import com.zagvladimir.domain.enums.Status;
import lombok.Data;

@Data
public class GradeCreateRequest {
    private long itemLeasedId;
    private long userFromId;
    private long userToId;
    private String description;
    private double grade;
    private Status status = Status.ACTIVE;
}
