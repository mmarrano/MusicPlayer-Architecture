package com.smartsync.dto;

public class SoundFileDTO {

    private Long userId;
    private String title;
    private String artist;
    private String filepath;

    public SoundFileDTO(Long userId, String title, String artist, String filepath) {
        this.userId = userId;
        this.title = title;
        this.artist = artist;
        this.filepath = filepath;
    }

    public SoundFileDTO() {
        // default constructor
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
}
