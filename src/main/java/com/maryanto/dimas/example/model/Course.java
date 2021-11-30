package com.maryanto.dimas.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    private String name;
    private String url;
    private String refCode;
    private String couponCode;
}
