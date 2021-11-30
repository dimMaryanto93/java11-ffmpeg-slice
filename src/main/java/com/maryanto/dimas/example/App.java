package com.maryanto.dimas.example;

import com.maryanto.dimas.example.model.Timeline;
import com.maryanto.dimas.example.model.Video;
import com.maryanto.dimas.example.services.FFMpegService;
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
                .pathToVideo("/Volumes/SamsungSSD/Udemy/docker/exports/12b-gitlab-ci-yaml.mp4")
                .markers("")
                .timelines(Arrays.asList(
                        new Timeline("01-what-is-gitlab-ci-file.mp4", "00:00:00", "00:05:51"),
                        new Timeline("02-basic-usage-gitlab-ci.mp4", "00:05:51", "00:17:04"),
                        new Timeline("03-gitlab-ci-workflows.mp4", "00:17:04", "00:26:10"),
                        new Timeline("04-gitlab-ci-variables.mp4", "00:26:10", "00:49:03"),
                        new Timeline("05-gitlab-ci-services.mp4", "00:49:03", "00:59:02"),
                        new Timeline("06-gitlab-ci-dind.mp4", "00:59:02", "01:11:54"),
                        new Timeline("07-gitlab-ci-cleanup.mp4", "01:11:54", "01:16:12")
                ))
                .build();
    }
}
