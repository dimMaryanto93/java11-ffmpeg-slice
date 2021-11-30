package com.maryanto.dimas.example.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Course {

    private String name;
    private String url;
    private String refCode;
    private String couponCode;
}
