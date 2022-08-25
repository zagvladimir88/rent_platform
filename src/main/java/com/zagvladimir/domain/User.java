package com.zagvladimir.domain;

import lombok.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.sql.Timestamp;

@Setter
@Getter
@EqualsAndHashCode
@Builder
//@ToString(exclude = {"userName"})
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;

    private String username;

    private String user_password;

    private int location_id;

    private String location_details;

    private String phone_number;

    private String mobile_number;

    private String email;

    private Timestamp registration_date;

    private Timestamp creation_date;

    private Timestamp modification_date;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
