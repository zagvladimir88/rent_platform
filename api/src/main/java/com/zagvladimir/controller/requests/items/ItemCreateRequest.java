package com.zagvladimir.controller.requests.items;

import com.zagvladimir.domain.enums.Status;
import lombok.Data;

@Data
public class ItemCreateRequest {

    private String itemName;

    private long itemTypeId;

    private Long locationId;

    private String itemLocation;

    private String description;

    private long ownerId;

    private int pricePerHour;

    private Boolean available;

    private Status status = Status.ACTIVE;

}
