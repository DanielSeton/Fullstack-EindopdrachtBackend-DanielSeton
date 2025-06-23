package com.eindopdracht.DJCorner.models;

import jakarta.persistence.*;

import java.io.File;

@Entity
@Table(name = "playlists")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private File musicFiles;
    private String genre;

    public Playlist() { }

    public Playlist(String genre, File musicFiles) {
        this.genre = genre;
        this.musicFiles = musicFiles;
    }

    public Long getId() {
        return id;
    }

    public File getMusicFiles() {
        return musicFiles;
    }

    public void setMusicFiles(File musicFiles) {
        this.musicFiles = musicFiles;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
