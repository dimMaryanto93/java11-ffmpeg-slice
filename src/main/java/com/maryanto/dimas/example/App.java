package com.maryanto.dimas.example;

import com.maryanto.dimas.example.model.Video;
import com.maryanto.dimas.example.services.FFMpegService;
import com.maryanto.dimas.example.services.JsonTemplateLoaderService;
import com.maryanto.dimas.example.services.ScreenflowMarkersToYoutubeClipService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
public class App {

    public static void main(String[] args) throws IOException {
        FFMpegService convert = new FFMpegService();
        String version = convert.getFFMpegVersion();
        log.info("ffmpeg version is {}", version);
        Video video;
        Optional<String> param = Arrays.stream(args).findFirst();

        if (param.isPresent()){
            video = JsonTemplateLoaderService.getFromExternal(param.get());
        }else {
            video = JsonTemplateLoaderService.getFromLocalResources("/video.template.json");
        }
        video.getTimelines().forEach(time -> {
            try {
                convert.splitVideo(video.getCourseName(), video.getSectionName(), video.getPathToVideo(), time);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        String marker = ScreenflowMarkersToYoutubeClipService.convert(video.getMarkers());
        log.info("---marker---\n{}", marker);
    }
}
