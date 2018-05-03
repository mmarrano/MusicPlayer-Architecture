package com.smartsync.model;

import com.smartsync.dto.SoundFileDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SoundFile {

    @GeneratedValue @Id
    private Long id;

    private Long userId;
    private String title;
    private String artist;
    private String filepath;

    public SoundFile(Long userId, String title, String artist, String filepath) {
        this.userId = userId;
        this.title = title;
        this.artist = artist;
        this.filepath = filepath;
    }

    public SoundFile(SoundFileDTO soundFileDTO) {
        this.userId = soundFileDTO.getUserId();
        this.title = soundFileDTO.getTitle();
        this.artist = soundFileDTO.getArtist();
        this.filepath = soundFileDTO.getFilepath();
    }

    public SoundFile() {
        // default constructor
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long id) {
        this.userId = userId;
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

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public String toString() {
        return "SoundFile{" +
                "id=" + id +
                ", title=" + title +
                ", artist='" + artist +
                ", filepath=" + filepath +
                '}';
    }
}
