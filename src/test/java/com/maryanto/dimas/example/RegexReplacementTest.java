package com.maryanto.dimas.example;

import com.maryanto.dimas.example.services.ScreenflowMarkersToYoutubeClipService;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class RegexReplacementTest extends TestCase {

    String text = "CHAPTER00=00:00:00.000\n" +
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
            "CHAPTER06NAME=End\n";

    @Test
    public void testConvertRegex() {
        String value = ScreenflowMarkersToYoutubeClipService.convert(text);
        log.info("value: \n{}", value);
    }
}
