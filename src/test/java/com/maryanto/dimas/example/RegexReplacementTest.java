package com.maryanto.dimas.example;

import com.maryanto.dimas.example.model.Timeline;
import com.maryanto.dimas.example.services.ScreenflowMarkersToYoutubeClipService;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

@Slf4j
public class RegexReplacementTest extends TestCase {

    String text = "CHAPTER00=00:00:00.000\n" +
            "CHAPTER00NAME=Introduction\n" +
            "CHAPTER01=00:01:23.283\n" +
            "CHAPTER01NAME=Compose_file for dev\n" +
            "CHAPTER02=00:06:24.283\n" +
            "CHAPTER02NAME=Compose file ?for/ prod\n" +
            "CHAPTER03=00:14:54.249\n" +
            "CHAPTER03NAME=Push your docker image to hub.docker\n" +
            "CHAPTER04=00:17:01.665\n" +
            "CHAPTER04NAME=Push to docker insecure registry\n" +
            "CHAPTER05=00:19:58.881\n" +
            "CHAPTER05NAME=Cleanup\n" +
            "CHAPTER06=00:23:30.897\n" +
            "CHAPTER06NAME=End\n";

    @Test
    public void testConvertToYoutubeDescription() {
        String value = ScreenflowMarkersToYoutubeClipService.convertToYoutubeDescription(text);
        log.info("value: \n{}", value);
    }

    @Test
    public void testConvertToTimeline() {
        List<Timeline> timelines = ScreenflowMarkersToYoutubeClipService.convertToTimeline(text);
        log.info("timeline: {}", timelines);
    }
}
