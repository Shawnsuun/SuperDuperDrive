package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating FileService bean");
    }

    File getFile(int fileId) {
        return fileMapper.getFile(fileId);
    }

    public List<File> getUserFiles(Integer userId) {
        return fileMapper.getUserFiles(userId);
    }

    List<File> getAllFiles() {
        return fileMapper.getAllFiles();
    }

    Integer addFile(File file) {
        return fileMapper.addFile(file);
    }

    Integer delete(int fileId) {
        return fileMapper.delete(fileId);
    }
}
