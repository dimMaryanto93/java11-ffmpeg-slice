package com.maryanto.dimas.example;

import com.maryanto.dimas.example.model.Video;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFmpegUtils;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.progress.Progress;
import net.bramp.ffmpeg.progress.ProgressListener;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class App {
    FFmpeg ffmpeg;
    FFprobe ffprobe;

    public App() throws IOException {
        this.ffmpeg = new FFmpeg();
        this.ffprobe = new FFprobe();
    }

    public static String getHomeDir() {
        return System.getProperty("user.home");
    }

    public void splitVideo(String courseName, String section, String file, Video timeline) throws IOException {
        FFmpegProbeResult input = ffprobe.probe(file);

        // count between 00:00:00 to startAt video play
        LocalTime start = LocalTime.parse("00:00:00");
        long startOffset = start.until(timeline.getTimeStart(), ChronoUnit.SECONDS);

        // count between startAt to end playback
        long duration = timeline.getTimeStart().until(timeline.getTimeEnd(), ChronoUnit.SECONDS);

        String outputDir = new StringBuilder(getHomeDir()).append(File.separator)
                .append("Videos").append(File.separator)
                .append("Udemy").append(File.separator)
                .append(courseName).append(File.separator)
                .append(section).append(File.separator).toString();

        File dir = new File(outputDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        FFmpegBuilder builder = this.ffmpeg.builder().addInput(input).overrideOutputFiles(true)
                .addOutput(outputDir + timeline.getExportFilename())
                    .setStartOffset(startOffset, TimeUnit.SECONDS)
                    .setDuration(duration, TimeUnit.SECONDS)
                    .setVideoResolution(1920, 1080)
                .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        executor.createJob(builder, new ProgressListener() {

            final double duration_ns = input.getFormat().duration * TimeUnit.SECONDS.toNanos(1);

            @Override
            public void progress(Progress progress) {
                double percentage = progress.out_time_ns / duration_ns;
                log.info("\nexported filename: {} -> {} \nstatus: {} time: {} ms",
                        timeline.getExportFilename(),
                        String.format("[%.0f%%]", (percentage * 100)),
                        progress.status,
                        FFmpegUtils.toTimecode(progress.out_time_ns, TimeUnit.NANOSECONDS)
                );
            }
        }).run();

    }

    public static void main(String[] args) throws IOException {
        App convert = new App();
        String version = convert.ffmpeg.version();
        log.info("ffmpeg version is {}", version);

        List<Video> timelines = Arrays.asList(
                new Video("01-what-is-gitlab-ci-file.mp4", "00:00:00", "00:05:51"),
                new Video("02-basic-usage-gitlab-ci.mp4", "00:05:51", "00:17:04"),
                new Video("03-gitlab-ci-workflows.mp4", "00:17:04", "00:26:10"),
                new Video("04-gitlab-ci-variables.mp4", "00:26:10", "00:49:03"),
                new Video("05-gitlab-ci-services.mp4", "00:49:03", "00:59:02"),
                new Video("06-gitlab-ci-dind.mp4", "00:59:02", "01:11:54"),
                new Video("07-gitlab-ci-cleanup.mp4", "01:11:54", "01:16:12")
        );

        for (Video video : timelines) {
            convert.splitVideo("docker", "11-study-cases","/Volumes/SamsungSSD/Udemy/docker/exports/12b-gitlab-ci-yaml.mp4", video);
        }

    }
}
