package com.eindopdracht.DJCorner.init;

import com.eindopdracht.DJCorner.enums.Status;
import com.eindopdracht.DJCorner.exceptions.ResourceNotFoundException;
import com.eindopdracht.DJCorner.models.*;
import com.eindopdracht.DJCorner.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Component
@Profile("!test")
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
        loadSubmission("test_track.mp3", "Demo Track", 128, 1L, List.of("Boogie", "EDM"), Status.NO_FEEDBACK, "");
        loadSubmission("test_track.mp3", "Experiment", 320, 2L, List.of("Dubstep", "Drill", "Boogie", "EDM"), Status.NO_FEEDBACK, "");
        loadSubmission("test_track.mp3", "Coole beats", 200, 1L, List.of("Pop", "Punk", "Hardcore"), Status.NO_FEEDBACK, "");
        loadSubmission("test_track.mp3", "Insane funk", 128, 5L, List.of("Jazz", "Soul", "House", "Vocals included"), Status.IN_CONSIDERATION, "We like the beat, but the tone is new for us. We need to discuss this and we'll get back to you.");
        loadSubmission("test_track.mp3", "Sicke track", 600, 5L, List.of("Boogie", "EDM"), Status.REJECTED, "Style feels off compared to our current programming direction.");
        loadSubmission("test_track.mp3", "Solar Drift", 240, 2L, List.of("Dubstep"), Status.NO_FEEDBACK, "");
        loadSubmission("test_track.mp3", "PartyRock", 190, 5L, List.of("Rock", "No vocals", "Techno"), Status.IN_CONSIDERATION, "The drop feels underwhelming. Consider reworking it for more impact.");
        loadSubmission("test_track.mp3", "Demo Track2", 128, 2L, List.of("Soul", "EDM"), Status.IN_CONSIDERATION, "You’re close. Revisit the arrangement and bring out the key elements more.");
        loadSubmission("test_track.mp3", "Echo Pulse", 128, 1L, List.of("Boogie", "EDM"), Status.NO_FEEDBACK, "");
        loadSubmission("test_track.mp3", "Experiment", 320, 2L, List.of("Dubstep", "Drill", "Boogie", "EDM"), Status.NO_FEEDBACK, "");
        loadSubmission("test_track.mp3", "Coole beats", 200, 1L, List.of("Pop", "Punk", "Hardcore"), Status.APPROVED, "Professional quality and genre-fitting. Definitely show-ready.");
        loadSubmission("test_track.mp3", "Neon Mirage", 128, 5L, List.of("Jazz", "Soul", "House", "Vocals included"), Status.NO_FEEDBACK, "");
        loadSubmission("test_track.mp3", "Midnight Circuit", 600, 5L, List.of("Boogie", "EDM"), Status.APPROVED, "Solid beat and strong transitions. Adds value to the overall vibe");
        loadSubmission("test_track.mp3", "Beatssample2", 240, 2L, List.of("Dubstep"), Status.NO_FEEDBACK, "");
        loadSubmission("test_track.mp3", "Mono Drive", 190, 5L, List.of("Rock", "No vocals", "Techno"), Status.REJECTED, "The production lacks clarity, and the beat doesn't quite hold up.");
        loadSubmission("test_track.mp3", "Tunnel Vision", 128, 2L, List.of("Soul", "EDM"), Status.NO_FEEDBACK, "");

        PlaylistTrack track1 = loadPlaylistTrack("test_track.mp3", "TestTrackish", 5L);
        PlaylistTrack track2 = loadPlaylistTrack("test_track.mp3", "Testmagoo", 2L);
        PlaylistTrack track3 = loadPlaylistTrack("test_track.mp3", "TesteryTest", 5L);

        Playlist playlist = new Playlist();
        playlist.setTitle("Dummy Playlist");
        playlist.setGenre("Techno");
        playlist.setTracks(List.of(track1, track2, track3));

        playlistRepository.save(playlist);
    }

    private void loadSubmission(String fileName, String title, int bpm, Long userId, List<String> tagNames, Status status, String feedbackText) throws IOException {
        ClassPathResource audioFile = new ClassPathResource("audio/" + fileName);
        byte[] audioBytes = StreamUtils.copyToByteArray(audioFile.getInputStream());

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Object with id: " + userId + " not found"));

        Feedback feedback = new Feedback();

        Submission submission = new Submission();
        submission.setTitle(title);
        submission.setArtistName(user.getUsername());
        submission.setBpm(bpm);
        submission.setUploadDate(LocalDate.now());
        submission.setMusicFile(audioBytes);
        submission.setMusicFileName(fileName);
        submission.setMusicFileType("audio/mp3");

        feedback.setStatus(status);
        feedback.setFeedback(feedbackText != null ? feedbackText : "");
        submission.setFeedback(feedback);

        submission.setUser(user);

        List<Tag> tags = tagNames.stream()
                .map(tagName -> tagRepository.findByName(tagName)
                        .orElseThrow(() -> new ResourceNotFoundException("tag with name " + tagName + " not found")))
                .toList();
        submission.setTags(tags);

        submissionRepository.save(submission);
    }

    private PlaylistTrack loadPlaylistTrack(String fileName, String title, Long userId) throws IOException {
        ClassPathResource audioFile = new ClassPathResource("audio/" + fileName);
        byte[] audioBytes = StreamUtils.copyToByteArray(audioFile.getInputStream());

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Object with id: " + userId + " not found"));


        PlaylistTrack playlistTrack = new PlaylistTrack();

        playlistTrack.setTitle(title);
        playlistTrack.setUser(user);
        playlistTrack.setMusicFile(audioBytes);
        playlistTrack.setMusicFileName(fileName);
        playlistTrack.setMusicFileType("audio/mp3");

        return playlistTrackRepository.save(playlistTrack);
    }
}
