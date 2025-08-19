package com.eindopdracht.DJCorner.init;

import com.eindopdracht.DJCorner.exceptions.ResourceNotFoundException;
import com.eindopdracht.DJCorner.models.*;
import com.eindopdracht.DJCorner.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final PlaylistRepository playlistRepository;
    private final PlaylistTrackRepository playlistTrackRepository;


    public DataLoader(SubmissionRepository submissionRepository, UserRepository userRepository, TagRepository tagRepository, PlaylistRepository playlistRepository, PlaylistTrackRepository playlistTrackRepository) {
        this.submissionRepository = submissionRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.playlistRepository = playlistRepository;
        this.playlistTrackRepository = playlistTrackRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadSubmission("test_track.mp3", "Demo Track", 128, 1L, List.of("Boogie", "EDM"));
        loadSubmission("test_track.mp3", "Experiment", 320, 2L, List.of("Dubstep", "Drill", "Boogie", "EDM"));
        loadSubmission("test_track.mp3", "Coole beats", 200, 1L, List.of("Pop", "Punk", "Hardcore"));
        loadSubmission("test_track.mp3", "Insane funk", 128, 5L, List.of("Jazz", "Soul", "House", "Vocals included"));
        loadSubmission("test_track.mp3", "Sicke track", 600, 5L, List.of("Boogie", "EDM"));
        loadSubmission("test_track.mp3", "Beatssample2", 240, 2L, List.of("Dubstep"));
        loadSubmission("test_track.mp3", "PartyRock", 190, 5L, List.of("Rock", "No vocals", "Techno"));
        loadSubmission("test_track.mp3", "Demo Track2", 128, 2L, List.of("Soul", "EDM"));
        loadSubmission("test_track.mp3", "Demo Track", 128, 1L, List.of("Boogie", "EDM"));
        loadSubmission("test_track.mp3", "Experiment", 320, 2L, List.of("Dubstep", "Drill", "Boogie", "EDM"));
        loadSubmission("test_track.mp3", "Coole beats", 200, 1L, List.of("Pop", "Punk", "Hardcore"));
        loadSubmission("test_track.mp3", "Insane funk", 128, 5L, List.of("Jazz", "Soul", "House", "Vocals included"));
        loadSubmission("test_track.mp3", "Sicke track", 600, 5L, List.of("Boogie", "EDM"));
        loadSubmission("test_track.mp3", "Beatssample2", 240, 2L, List.of("Dubstep"));
        loadSubmission("test_track.mp3", "PartyRock", 190, 5L, List.of("Rock", "No vocals", "Techno"));
        loadSubmission("test_track.mp3", "Demo Track2", 128, 2L, List.of("Soul", "EDM"));

        loadPlaylistTrack("test_track.mp3", "TestTrackish", 5L);
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

    private void loadPlaylistTrack(String fileName, String title, Long userId) throws IOException {
        ClassPathResource audioFile = new ClassPathResource("audio/" + fileName);
        byte[] audioBytes = StreamUtils.copyToByteArray(audioFile.getInputStream());

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Object with id: " + userId + " not found"));


        PlaylistTrack playlistTrack = new PlaylistTrack();

        playlistTrack.setTitle(title);
        playlistTrack.setUser(user);
        playlistTrack.setMusicFile(audioBytes);
        playlistTrack.setMusicFileName(fileName);
        playlistTrack.setMusicFileType("audio/mp3");

        playlistTrackRepository.save(playlistTrack);
    }
}
