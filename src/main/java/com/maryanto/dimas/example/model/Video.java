package com.maryanto.dimas.example.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = {"timelines", "tags"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Video {

    private String pathToVideo;
    private String courseName;
    private String courseUrl;
    private String description;
    private String sectionName;
    private String markers;
    private boolean active;
    private List<String> tags = new ArrayList<>();
    private List<Timeline> timelines = new ArrayList<>();
}
