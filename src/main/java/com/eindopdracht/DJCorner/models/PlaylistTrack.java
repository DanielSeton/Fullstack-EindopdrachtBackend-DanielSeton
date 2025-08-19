package com.eindopdracht.DJCorner.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tracks")
public class PlaylistTrack {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    private byte[] musicFile;

    private String musicFileName;
    private String musicFileType;

    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    private List<Playlist> playlist = new ArrayList<>();

    public PlaylistTrack() { }

    public PlaylistTrack(Long id, byte[] musicFile, String musicFileName, String musicFileType, String title, User user, List<Playlist> playlist) {
        this.id = id;
        this.musicFile = musicFile;
        this.musicFileName = musicFileName;
        this.musicFileType = musicFileType;
        this.title = title;
        this.user = user;
        this.playlist = playlist;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getMusicFile() {
        return musicFile;
    }

    public void setMusicFile(byte[] musicFile) {
        this.musicFile = musicFile;
    }

    public String getMusicFileName() {
        return musicFileName;
    }

    public void setMusicFileName(String musicFileName) {
        this.musicFileName = musicFileName;
    }

    public String getMusicFileType() {
        return musicFileType;
    }

    public void setMusicFileType(String musicFileType) {
        this.musicFileType = musicFileType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Playlist> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<Playlist> playlist) {
        this.playlist = playlist;
    }
}
