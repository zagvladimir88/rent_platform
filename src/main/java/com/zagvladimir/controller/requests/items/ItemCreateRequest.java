package com.zagvladimir.controller.requests.items;

import com.zagvladimir.domain.Status;
import lombok.Data;

@Data
public class ItemCreateRequest {

    private String itemName;

    private int itemTypeId;

    private int locationId;

    private String itemLocation;

    private String description;

    private String ownerId;

    private int pricePerHour;

    private Boolean available;

    private Status status;

}
