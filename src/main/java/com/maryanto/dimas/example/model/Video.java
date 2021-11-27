package com.maryanto.dimas.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    private String exportFilename;
    private LocalTime timeStart;
    private LocalTime timeEnd;

    public Video(String exportFilename, String timeStart, String timeEnd) {
        this.exportFilename = exportFilename;
        this.timeStart = LocalTime.parse(timeStart);
        this.timeEnd = LocalTime.parse(timeEnd);
    }
}
