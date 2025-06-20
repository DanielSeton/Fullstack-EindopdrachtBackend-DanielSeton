package com.eindopdracht.DJCorner.models;

import jakarta.persistence.*;

import java.io.File;

@Entity
@Table(name = "submissions")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String artistName;
    private Integer bpm;
    private File musicFile;


    public Submission() { }

    public Submission(String title, String artistName, Integer bpm, File musicFile) {
        this.title = title;
        this.artistName = artistName;
        this.bpm = bpm;
        this.musicFile = musicFile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Integer getBpm() {
        return bpm;
    }

    public void setBpm(Integer bpm) {
        this.bpm = bpm;
    }

    public File getMusicFile() {
        return musicFile;
    }

    public void setMusicFile(File musicFile) {
        this.musicFile = musicFile;
    }
}
