package com.zagvladimir.dto;

import com.zagvladimir.domain.Status;
import lombok.*;

import java.sql.Timestamp;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

    private Long id;

    private String name;

    private Timestamp creationDate;

    private Timestamp modificationDate;

    private Status status;
}
