package com.zagvladimir.controller.requests.items;

import com.zagvladimir.domain.enums.Status;
import com.zagvladimir.domain.User;
import lombok.Data;

@Data
public class ItemCreateRequest {

    private String itemName;

    private long itemTypeId;

    private Long locationId;

    private String itemLocation;

    private String description;

    private User owner;

    private int pricePerHour;

    private Boolean available;

    private Status status = Status.ACTIVE;

}
