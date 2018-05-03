package com.smartsync.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SoundFileRepository extends JpaRepository<SoundFile, Long> {
    SoundFile findById(Long id);
    List<SoundFile> findByUserId(Long userId);
}