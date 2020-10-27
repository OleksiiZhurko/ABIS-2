package com.warehouse.warehouse.db;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cnap {

    private String name;
    private int age;
    private Long document;
    private LocalDate until;

}
