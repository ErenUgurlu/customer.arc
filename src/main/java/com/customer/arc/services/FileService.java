package com.customer.arc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customer.arc.entities.File;
import com.customer.arc.repositories.FileRepositoryJPA;

@Service
public class FileService {

    @Autowired
    private FileRepositoryJPA fileRepository;

    public File saveOrUpdateFile(File file) {
        return fileRepository.save(file);
    }

    public void deleteFile(Long id) {
        fileRepository.deleteById(id);
    }

    public List<File> getAllFiles() {
        return fileRepository.findAll();
    }

    public Optional<File> getFileById(Long id) {
        return fileRepository.findById(id);
    }
}