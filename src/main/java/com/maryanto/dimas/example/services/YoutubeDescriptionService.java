package com.maryanto.dimas.example.services;

import com.maryanto.dimas.example.model.Course;
import com.maryanto.dimas.example.model.Instructure;
import com.maryanto.dimas.example.model.Video;

import java.util.List;
import java.util.stream.Collectors;

public class YoutubeDescriptionService {

    public static String template(Video video) {
        Course course = video.getCourse();
        List<Instructure> instructures = course.getInstructures();
        List<String> tags = video.getTags();
        return String.format("%s\n\n%s\n\n%s\n\n%s\n%s\n\nTOC:\n%s",
                unlockVideo(course),
                video.getDescription(),
                instructureProfiles(instructures),
                socialMedia(),
                tags(tags),
                video.getTableOfContents()
        );
    }

    private static String unlockVideo(Course course) {
        return String.format("Unlock semua videonya, dengan join Premium member di link berikut:\n" +
                "%s?referralCode=%s", course.getUrl(), course.getRefCode());
    }

    private static String instructureProfiles(List<Instructure> instructures) {
        return String.format(
                "Pemateri: \n%s", instructures.stream()
                        .map(data -> String.format("%s <%s>", data.getName(), data.getEmail()))
                        .collect(Collectors.joining("\n"))
        );
    }

    private static String socialMedia() {
        return "folow me: https://linktr.ee/dimasm93\n" +
                "\n" +
                "Untuk mendukung channel ini, mau donasi: via https://saweria.co/DimasMaryanto Terimakasih ya!";
    }

    private static String tags(List<String> tags) {
        return tags.stream().map(data -> "#" + data).collect(Collectors.joining(" "));
    }
}
