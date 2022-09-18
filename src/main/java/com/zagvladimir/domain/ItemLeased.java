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
@Table(name = "items_leased")
public class ItemLeased extends BaseEntity{


    @Column(name = "item_id")
    private long itemId;

    @Column(name = "renter_id")
    private long renterId;

    @Column(name = "time_from")
    private Timestamp timeFrom;

    @Column(name = "time_to")
    private Timestamp timeTo;

    @Column(name = "price_per_hour")
    private double pricePerHour;

    @Column
    private double discount;

    @Column
    private double fee;

    @Column(name = "price_total")
    private double priceTotal;

    @Column(name = "rentier_grade_description")
    private String rentierGradeDescription;

    @Column(name = "renter_grade_description")
    private String renterGradeDescription;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }

}
