package com.maryanto.dimas.example.services;

import com.maryanto.dimas.example.model.Timeline;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class ScreenflowMarkersToYoutubeClipService {

    public static String convertToYoutubeDescription(String content) {
        Pattern pattern = Pattern.compile("^CHAPTER\\d*=(.*)\\.\\d\\d\\d$\\n.*=(.*)$", Pattern.MULTILINE);
        Matcher match = pattern.matcher(content);

        return match.replaceAll("$1 $2");
    }

    public static List<Timeline> convertToTimeline(String content) {
        String youtubeDescription = convertToYoutubeDescription(content);
        List<String> times = Arrays.asList(youtubeDescription.split("\\R+"));
        List<Timeline> timelines = new ArrayList<>();
        int counter = 1;
        for (String value : times.stream().limit(times.size() - 1).collect(Collectors.toList())) {
            String offsetTime = value.substring(0, 8);
            String endTime = times.get(counter).substring(0, 8);
            String description = value.substring(9, value.length())
                    .replaceAll("\\W+(?!$)", "-")
                    .replaceAll("[()_./]+", "")
                    .toLowerCase();
            timelines.add(
                    new Timeline(
                            String.format("%02d-%s.mp4", counter, description),
                            offsetTime,
                            endTime
                    )
            );
            counter++;
        }
        return timelines;
    }


}
