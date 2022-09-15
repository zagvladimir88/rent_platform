package com.zagvladimir.controller.requests.itemsLeased;

import com.zagvladimir.domain.Status;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ItemLeasedCreateRequest {


        private long item_id;

        private long renter_id;

        private Timestamp time_from;

        private Timestamp time_to;

        private double price_per_hour;

        private double discount;

        private double fee;

        private double price_total;

        private String  rentier_grade_description;

        private String  renter_grade_description;

        private Status status;


}
