package com.maryanto.dimas.example.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Video {

    private String pathToVideo;
    private String markers;
    private List<Timeline> timelines = new ArrayList<>();
}
