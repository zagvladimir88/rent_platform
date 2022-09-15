package com.zagvladimir.domain;


import lombok.Data;

import java.sql.Timestamp;

@Data
public class BaseEntity {

    private Long id;

    private Timestamp creation_date;

    private Timestamp modification_date;

    private Status status;
}
