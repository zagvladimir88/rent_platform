package com.zagvladimir.controller.response;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GradeResponse {

    private Long id;

    private Long itemId;

    private Long userId;

    private double grade;

    private String description;

}
