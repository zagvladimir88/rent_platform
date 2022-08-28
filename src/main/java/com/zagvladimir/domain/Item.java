package com.zagvladimir.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private Long id;

    private String item_name;

    private int item_type_id;

    private int location_id;

    private String item_location;

    private String description;

    private long owner_id;

    private double price_per_hour;

    private Boolean available;

    private Timestamp creation_date;

    private Timestamp modification_date;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }

}
