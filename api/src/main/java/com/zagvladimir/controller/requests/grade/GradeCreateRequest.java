package com.zagvladimir.controller.requests.grade;

import com.zagvladimir.domain.enums.Status;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class GradeCreateRequest {
    private long itemLeasedId;
    private long userFromId;
    private long userToId;
    private String description;
    private double grade;
    private Timestamp creationDate;
    private Timestamp modificationDate;
    private Status status = Status.ACTIVE;
}
