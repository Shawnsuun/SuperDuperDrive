package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private NoteService noteService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;
    private FileService fileService;
    private UserService userService;

    public HomeController(NoteService noteService, CredentialService credentialService, EncryptionService encryptionService, FileService fileService, UserService userService) {
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping
    public String getHomePage(Authentication authentication, Note note, Credential credential, Model model){
        String username = authentication.getName();
        model.addAttribute("notes",this.noteService.getUserNotes(this.userService.getUserId(username)));
        model.addAttribute("credentials",this.credentialService.getUserCredentials(this.userService.getUserId(username)));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("files",this.fileService.getUserFiles(this.userService.getUserId(username)));
        return "home";}
}
