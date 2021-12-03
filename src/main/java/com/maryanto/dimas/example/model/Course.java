package com.maryanto.dimas.example.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"instructures"})
public class Course {

    private String name;
    private List<Instructure> instructures = new ArrayList<>();
    private String url;
    private String refCode;
    private String couponCode;
}
