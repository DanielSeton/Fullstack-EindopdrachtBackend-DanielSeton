package com.eindopdracht.DJCorner.init;

import com.eindopdracht.DJCorner.exceptions.ResourceNotFoundException;
import com.eindopdracht.DJCorner.models.Submission;
import com.eindopdracht.DJCorner.models.Tag;
import com.eindopdracht.DJCorner.models.User;
import com.eindopdracht.DJCorner.repositories.SubmissionRepository;
import com.eindopdracht.DJCorner.repositories.TagRepository;
import com.eindopdracht.DJCorner.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;


    public DataLoader(SubmissionRepository submissionRepository, UserRepository userRepository, TagRepository tagRepository) {
        this.submissionRepository = submissionRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadSubmission("test_track.mp3", "Demo Track", 128, 1L, List.of("Boogie", "EDM"));
        loadSubmission("test_track.mp3", "Demo Track", 128, 2L, List.of("Emo", "EDM"));

    }

    private void loadSubmission(String fileName, String title, int bpm, Long userId, List<String> tagNames) throws IOException {
        ClassPathResource audioFile = new ClassPathResource("audio/" + fileName);
        byte[] audioBytes = StreamUtils.copyToByteArray(audioFile.getInputStream());

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Object with id: " + userId + " not found"));

        Submission submission = new Submission();
        submission.setTitle(title);
        submission.setArtistName(user.getUsername());
        submission.setBpm(bpm);
        submission.setUploadDate(LocalDate.now());
        submission.setMusicFile(audioBytes);
        submission.setMusicFileName(fileName);
        submission.setMusicFileType("audio/mp3");

        submission.setUser(user);
        submission.setFeedback(null);

        List<Tag> tags = tagNames.stream()
                .map(tagName -> tagRepository.findByName(tagName)
                        .orElseThrow(() -> new ResourceNotFoundException("tag with name " + tagName + " not found")))
                .toList();
        submission.setTags(tags);

        submissionRepository.save(submission);
    }
}
