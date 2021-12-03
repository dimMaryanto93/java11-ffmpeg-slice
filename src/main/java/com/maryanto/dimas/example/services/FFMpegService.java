package com.maryanto.dimas.example.services;

import com.maryanto.dimas.example.model.Timeline;
import com.maryanto.dimas.example.model.Video;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFmpegUtils;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.info.Codec;
import net.bramp.ffmpeg.info.Format;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.progress.Progress;
import net.bramp.ffmpeg.progress.ProgressListener;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
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

    public void getTotalDurationOfVideo(Video video) throws IOException {
        FFmpegProbeResult input = ffprobe.probe(video.getPathToVideo());

    }

    public void splitVideo(Video video, Timeline timeline) throws IOException {
        // count between 00:00:00 to startAt video play
        LocalTime start = LocalTime.parse("00:00:00");
        long startOffset = start.until(timeline.getTimeStart(), ChronoUnit.SECONDS);

        // count between startAt to end playback
        long duration = timeline.getTimeStart().until(timeline.getTimeEnd(), ChronoUnit.SECONDS);

        File originalFile = new File(video.getPathToVideo());

        if (!originalFile.exists())
            throw new IOException("File video not found");

        if (originalFile.isDirectory())
            throw new IOException("File is video, change to video path");

        File dir = new File(originalFile.getParentFile().getAbsolutePath() + File.separator + FilenameUtils.removeExtension(originalFile.getName()));
        String exportedFile = dir.getAbsolutePath() + File.separator + timeline.getExportFilename();
        log.info("file will be available after slice on {}", exportedFile);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        FFmpegProbeResult input = ffprobe.probe(video.getPathToVideo());

        FFmpegBuilder builder = this.ffmpeg.builder()
                .addInput(input).overrideOutputFiles(true)
                .addOutput(exportedFile)
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
