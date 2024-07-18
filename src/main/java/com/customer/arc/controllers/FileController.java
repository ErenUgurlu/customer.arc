package com.customer.arc.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer.arc.entities.File;
import com.customer.arc.services.FileService;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping
    public File createFile(@RequestBody File file) {
        return fileService.saveOrUpdateFile(file);
    }

    @PostMapping("/update")
    public ResponseEntity<File> updateFile( @RequestBody File fileDetails) {
        Optional<File> fileOptional = fileService.getFileById(fileDetails.getId());
        if (fileOptional.isPresent()) {
            File file = fileOptional.get();
            file.setFileName(fileDetails.getFileName());
            file.setCustomer(fileDetails.getCustomer());
            final File updatedFile = fileService.saveOrUpdateFile(file);
            return ResponseEntity.ok(updatedFile);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) {
        fileService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<File> getAllFiles() {
        return fileService.getAllFiles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<File> getFileById(@PathVariable Long id) {
        Optional<File> fileOptional = fileService.getFileById(id);
        if (fileOptional.isPresent()) {
            return ResponseEntity.ok(fileOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
