package com.maryanto.dimas.example.services;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ScreenflowMarkersToYoutubeClipService {

    public static String convert(String content){
        Pattern pattern = Pattern.compile("^CHAPTER\\d*=(.*)\\.\\d\\d\\d$\\n.*=(.*)$", Pattern.MULTILINE);
        Matcher match = pattern.matcher(content);

        return match.replaceAll("$1 $2");
    }
}
