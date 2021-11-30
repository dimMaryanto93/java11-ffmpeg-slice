package com.maryanto.dimas.example;

import com.maryanto.dimas.example.model.Timeline;
import com.maryanto.dimas.example.model.Video;
import com.maryanto.dimas.example.services.FFMpegService;
import com.maryanto.dimas.example.services.ScreenflowMarkersToYoutubeClipService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class App {

    public static void main(String[] args) throws IOException {
        FFMpegService convert = new FFMpegService();
        String version = convert.getFFMpegVersion();
        log.info("ffmpeg version is {}", version);

        Video video = Video.builder()
                .courseName("docker")
                .sectionName("09-study-cases")
                .pathToVideo("/Volumes/SamsungSSD/Udemy/docker/exports/10a-compose-php-deploy.mp4")
                .markers("CHAPTER00=00:00:00.000\n" +
                        "CHAPTER00NAME=Introduction\n" +
                        "CHAPTER01=00:01:23.283\n" +
                        "CHAPTER01NAME=Compose file for dev\n" +
                        "CHAPTER02=00:06:24.283\n" +
                        "CHAPTER02NAME=Compose file for prod\n" +
                        "CHAPTER03=00:14:54.249\n" +
                        "CHAPTER03NAME=Push your docker image to hub.docker\n" +
                        "CHAPTER04=00:17:01.665\n" +
                        "CHAPTER04NAME=Push to docker insecure registry\n" +
                        "CHAPTER05=00:19:58.881\n" +
                        "CHAPTER05NAME=Cleanup\n" +
                        "CHAPTER06=00:23:30.897\n" +
                        "CHAPTER06NAME=End\n")
                .tags(Arrays.asList("docker", "compose", "php", "studycases", "udemy"))
                .timelines(Arrays.asList(
                        new Timeline("01-php-intro.mp4", "00:00:00", "00:01:23"),
                        new Timeline("02-php-compose-dev.mp4", "00:01:23", "00:06:24"),
                        new Timeline("03-php-compose-prod.mp4", "00:06:24", "00:14:54"),
                        new Timeline("04-php-push-docker-hub.mp4", "00:14:54", "00:17:01"),
                        new Timeline("05-php-push-insecure-registry.mp4", "00:17:01", "00:19:58"),
                        new Timeline("06-php-cleanup.mp4", "00:19:58", "00:23:30")
                ))
                .build();

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
