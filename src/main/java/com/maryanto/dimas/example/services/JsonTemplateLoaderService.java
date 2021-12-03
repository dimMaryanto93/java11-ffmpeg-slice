package com.maryanto.dimas.example.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.maryanto.dimas.example.model.Video;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class JsonTemplateLoaderService {

    public static Video getFromExternal(String filename) throws IOException {
        String content = FileUtils.readFileToString(new File(filename), StandardCharsets.UTF_8);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(content, Video.class);
    }

}
