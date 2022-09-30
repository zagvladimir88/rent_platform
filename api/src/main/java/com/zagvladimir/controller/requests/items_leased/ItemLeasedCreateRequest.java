package com.zagvladimir.controller.requests.items_leased;

import com.zagvladimir.domain.enums.Status;
import com.zagvladimir.domain.User;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ItemLeasedCreateRequest {


        private long itemId;

        private User renter;

        private Timestamp timeFrom;

        private Timestamp timeTo;

        private double pricePerHour;

        private double discount;

        private double fee;

        private double priceTotal;

        private String  rentierGradeDescription;

        private String  renterGradeDescription;

        private Status status = Status.ACTIVE;


}
