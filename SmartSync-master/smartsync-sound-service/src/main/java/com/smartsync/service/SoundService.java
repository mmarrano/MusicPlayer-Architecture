package com.smartsync.service;

import com.smartsync.dto.SoundFileDTO;
import com.smartsync.model.SoundFile;
import com.smartsync.model.SoundFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SoundService {

    @Autowired
    private SoundFileRepository soundFileRepository;

    public SoundService() {

    }

    public List<SoundFile> getAllSoundFile() {
        return this.soundFileRepository.findAll();
    }

    public SoundFile addSoundFile(SoundFileDTO soundFileDTO) {
        SoundFile soundFile = new SoundFile(soundFileDTO);
        this.soundFileRepository.save(soundFile);

        return soundFile;
    }

    public SoundFile getSoundfileById(Long id) {
        return this.soundFileRepository.findById(id);
    }

    public SoundFile deleteSoundFileById(Long id) {
        SoundFile soundFile = this.soundFileRepository.findById(id);

        this.soundFileRepository.delete(soundFile);

        return soundFile;
    }

    public List<SoundFile> getSoundFilesForUser(Long userId) {
        return this.soundFileRepository.findByUserId(userId);
    }

    public List<SoundFile> deleteSoundFilesForUser(Long userId) {
        List<SoundFile> soundFileList = this.soundFileRepository.findByUserId(userId);

        this.soundFileRepository.delete(soundFileList);

        return soundFileList;
    }
}
