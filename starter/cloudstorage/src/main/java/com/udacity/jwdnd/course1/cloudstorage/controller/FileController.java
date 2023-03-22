package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/files")
public class FileController {
    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("fileUpload") MultipartFile multipartFile,
                         Authentication authentication, Model model) throws IOException {

        // Get the current user's ID
        Integer userId = userService.getUser(authentication.getName()).getUserId();

        // Check if file is empty
        if (multipartFile.isEmpty()) {
            model.addAttribute("error", "Please select a file to upload.");
            return "result";
        }
        Integer res = fileService.addFile(multipartFile, authentication.getName());
        model.addAttribute("success", "File uploaded successfully!");
        return "result";
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downLoad(@RequestParam String fileName, Authentication authentication, Model model) {
        Integer userId = userService.getUserId(authentication.getName());
        // Retrieve the file data from the FileService
        File file = fileService.getUserFile(userId, fileName);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }
        byte[] fileData = file.getFileData();

        // Create a ByteArrayResource from the file data
        ByteArrayResource resource = new ByteArrayResource(fileData);

        // Set the content-type header and attachment filename
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, file.getContentType());

        // Return a ResponseEntity with the file data, headers
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    @GetMapping("/deleteFile")
    public String delete(@RequestParam Integer fileId, Authentication authentication, Model model) {
        Integer rowsDeleted = fileService.delete(fileId);
        if (rowsDeleted > 0) {
            model.addAttribute("success", "File deleted successfully!");
        } else {
            model.addAttribute("error", "There was an error deleting the file. Please try again.");
        }
        return "result";
    }
}
