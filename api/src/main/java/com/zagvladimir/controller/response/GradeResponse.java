package com.zagvladimir.controller.response;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GradeResponse {

    private Long id;

    private Long itemLeasedId;

    private Long userFromId;

    private Long userToId;

    private double grade;

    private String description;

}
