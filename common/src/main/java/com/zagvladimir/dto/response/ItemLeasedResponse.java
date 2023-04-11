package com.zagvladimir.dto.response;

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

    private double pricePerDay;

    private double discount;

    private double priceTotal;
}
