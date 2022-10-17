package com.zagvladimir.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ItemLeasedResponse {

    private Long id;

    private long itemId;

    private Long renterId;

    private Timestamp timeFrom;

    private Timestamp timeTo;

    private double pricePerHour;

    private double discount;

    private double fee;

    private double priceTotal;

    private String rentierGradeDescription;

    private String renterGradeDescription;

}
