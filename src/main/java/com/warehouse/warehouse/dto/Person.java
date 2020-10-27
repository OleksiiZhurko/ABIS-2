package com.warehouse.warehouse.dto;

import com.warehouse.warehouse.db.Work;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Person {

    private Long document;
    private Long codeId;
    private String name;
    private Work work;

}
