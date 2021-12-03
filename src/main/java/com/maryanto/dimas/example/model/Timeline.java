package com.maryanto.dimas.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Timeline {

    private String exportFilename;
    private LocalTime timeStart;
    private LocalTime timeEnd;

    public Timeline(String exportFilename, String timeStart, String timeEnd) {
        this.exportFilename = exportFilename;
        this.timeStart = LocalTime.parse(timeStart);
        this.timeEnd = LocalTime.parse(timeEnd);
    }
}
