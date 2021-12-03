package com.maryanto.dimas.example;

import com.maryanto.dimas.example.model.Timeline;
import com.maryanto.dimas.example.model.Video;
import com.maryanto.dimas.example.services.FFMpegService;
import com.maryanto.dimas.example.services.JsonTemplateLoaderService;
import com.maryanto.dimas.example.services.ScreenflowMarkersToYoutubeClipService;
import com.maryanto.dimas.example.services.YoutubeDescriptionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
public class App {

    public static void main(String[] args) throws IOException {
        FFMpegService convert = new FFMpegService();
        String version = convert.getFFMpegVersion();
        log.info("ffmpeg version is {}", version);

        Optional<String> videoFilename = Arrays.stream(args).findFirst();
        Video video;
        if (videoFilename.isPresent()) {
            video = JsonTemplateLoaderService.getFromExternal(videoFilename.get());
        } else {
            throw new FileNotFoundException("template.json argument is required! ex: java -jar application.jar path-to/template.json");
        }

        if (!video.getPathToMarker().isBlank()) {
            File file = new File(video.getPathToMarker());
            if (!file.exists()) {
                throw new FileNotFoundException("marker from subler file " + video.getPathToMarker() + " not found!");
            }
            String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            video.setTimelines(ScreenflowMarkersToYoutubeClipService.convertToTimeline(content));
            video.setTableOfContents(ScreenflowMarkersToYoutubeClipService.convertToYoutubeDescription(content));

            String template = YoutubeDescriptionService.template(video);
            log.info("youtube description: \n" +
                    "----------------------------" +
                    "\n{}\n" +
                    "----------------------------", template);
        }

        for (Timeline time : video.getTimelines()) {
            log.info("timeline: {}", time);
            convert.splitVideo(video, time);
        }
    }
}
