package com.maryanto.dimas.example.services;

import com.maryanto.dimas.example.model.Timeline;
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
import java.util.concurrent.TimeUnit;

@Slf4j
public class FFMpegService {

    FFmpeg ffmpeg;
    FFprobe ffprobe;

    public FFMpegService() throws IOException {
        this.ffmpeg = new FFmpeg();
        this.ffprobe = new FFprobe();
    }

    public static String getHomeDir() {
        return System.getProperty("user.home");
    }

    public String getFFMpegVersion() throws IOException {
        return this.ffmpeg.version();
    }

    public String getFFProbeVersion() throws IOException {
        return this.ffprobe.version();
    }

    public void splitVideo(String courseName, String section, String file, Timeline timeline) throws IOException {
        // count between 00:00:00 to startAt video play
        LocalTime start = LocalTime.parse("00:00:00");
        long startOffset = start.until(timeline.getTimeStart(), ChronoUnit.SECONDS);

        // count between startAt to end playback
        long duration = timeline.getTimeStart().until(timeline.getTimeEnd(), ChronoUnit.SECONDS);

        File originalFile = new File(file);
        if (!originalFile.exists())
            throw new IOException("File video not found");

        if (originalFile.isDirectory())
            throw new IOException("File is video, change to video path");

        FFmpegProbeResult input = ffprobe.probe(file);

        String outputDir = new StringBuilder(getHomeDir()).append(File.separator)
                .append("Videos").append(File.separator)
                .append("Udemy").append(File.separator)
                .append(courseName).append(File.separator)
                .append(section).append(File.separator)
                .append(originalFile.getName()).append(File.separator)
                .toString();

        File dir = new File(outputDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        FFmpegBuilder builder = this.ffmpeg.builder().addInput(input).overrideOutputFiles(true)
                .addOutput(outputDir + timeline.getExportFilename())
                    .setStartOffset(startOffset, TimeUnit.SECONDS)
                    .setDuration(duration, TimeUnit.SECONDS)
                    .setVideoResolution(1920, 1080)
                    .setVideoCodec("copy")
                    .setAudioCodec("copy")
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
}
