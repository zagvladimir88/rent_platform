package com.zagvladimir.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "items")
public class Item extends BaseEntity{

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_type_id")
    private int itemTypeId;

    @Column(name = "location_id")
    private int locationId;

    @Column(name = "item_location")
    private String itemLocation;

    @Column(name = "description")
    private String description;

    @Column(name = "owner_id")
    private long ownerId;

    @Column(name = "price_per_hour")
    private double pricePerHour;

    @Column(name = "available")
    private Boolean available;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }

}
