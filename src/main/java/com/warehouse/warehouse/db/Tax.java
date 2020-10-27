package com.warehouse.warehouse.db;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Tax {

    private String name;
    private String sureName;
    private String middleName;
    private Long codeId;
    private Work status;

}
