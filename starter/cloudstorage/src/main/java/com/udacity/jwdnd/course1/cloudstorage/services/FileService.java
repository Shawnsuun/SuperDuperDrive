package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;
    private UserMapper userMapper;

    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating FileService bean");
    }

    File getFile(int fileId) {
        return fileMapper.getFile(fileId);
    }

    public File getUserFile(Integer userId, String fileName) {
        return fileMapper.getUserFile(userId, fileName);
    }

    public List<File> getUserFiles(Integer userId) {
        return fileMapper.getUserFiles(userId);
    }

    List<File> getAllFiles() {
        return fileMapper.getAllFiles();
    }

    public Integer addFile(MultipartFile uploadFile, String userName) throws IOException {
        Integer fileId = null;
        String fileName = uploadFile.getOriginalFilename();
        Integer userId = userMapper.getUser(userName).getUserId();
        String contentType = uploadFile.getContentType();
        String fileSize = String.valueOf(uploadFile.getSize());
        byte[] fileData = uploadFile.getBytes();
        return fileMapper.addFile(
            new File(
                fileId,
                fileName,
                contentType,
                fileSize,
                userId,
                fileData));
    }

    public Integer delete(int fileId) {
        return fileMapper.delete(fileId);
    }

}
