package com.zagvladimir.controller.requests.items;

import com.zagvladimir.domain.Status;
import lombok.Data;

@Data
public class ItemCreateRequest {

    private String item_name;

    private int item_type_id;

    private int location_id;

    private String item_location;

    private String description;

    private String owner_id;

    private int price_per_hour;

    private Boolean available;

    private Status status;

}
