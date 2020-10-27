package com.warehouse.warehouse.db;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PensionFund {

    private String name;
    private String sureName;
    private String middleName;
    private Long document;
    private Long codeId;

}
