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
public class ItemLeased {

    private int id;

    private int item_id;

    private int renter_id;

    private Timestamp time_from;

    private Timestamp time_to;

    private double price_per_hour;

    private double discount;

    private double fee;

    private double price_total;

    private String rentier_grade_description;

    private String renter_grade_description;

    private Timestamp creation_date;

    private Timestamp modification_date;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }

}
