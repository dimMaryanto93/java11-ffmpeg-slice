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
    private String pathToMarker;
    private Course course;
    private String description;
    private String tableOfContents;
    private List<String> tags = new ArrayList<>();
    private List<Timeline> timelines = new ArrayList<>();
}
