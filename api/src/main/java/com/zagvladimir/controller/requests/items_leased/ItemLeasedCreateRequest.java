package com.zagvladimir.controller.requests.items_leased;

import com.zagvladimir.domain.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.sql.Timestamp;

@Data
public class ItemLeasedCreateRequest {

        @Schema(defaultValue = "2", type = "Long" , description = "The number of the item that is being rented")
        private long itemId;

        @Schema(defaultValue = "3", type = "Long" , description = "The number of the users who owns")
        private long renterId;

        @Schema(defaultValue = "2022-09-06 16:20:05.000000", type = "string($date-time)" , description = "Rental start date")
        @NotNull
        private Timestamp timeFrom;

        @Schema(defaultValue = "2022-09-07 16:20:05.000000", type = "string($date-time)" , description = "End date of the lease")
        @NotNull
        private Timestamp timeTo;

        @Schema(defaultValue = "5.0", type = "number($double)", description = "Price per hour")
        @Positive
        private double pricePerHour;

        @Schema(defaultValue = "5.0", type = "number($double)" , description = "Discount")
        private double discount;

        @Schema(defaultValue = "0.1", type = "number($double)" , description = "Fee")
        private double fee;

        @Schema(defaultValue = "8", type = "number($double)" , description = "Total Price")
        private double priceTotal;

        @Schema(defaultValue = "test", type = "string" , description = "Renter grade")
        private String  rentierGradeDescription;

        @Schema(defaultValue = "test", type = "string" , description = "Rentier grade")
        private String  renterGradeDescription;

        @Schema(defaultValue = "ACTIVE", type = "string" , description = "Status")
        private Status status = Status.ACTIVE;


}
