package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private NoteService noteService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;
    private FileService fileService;
    private UserService userService;


    @GetMapping("/home")
    public String getHomePage(Authentication authentication, Note note, Credential credential, Model model){
        String username = authentication.getName();
        model.addAttribute("homeNotes",this.noteService.getUserNotes(this.userService.getUserId(username)));
        model.addAttribute("homeCredentials",this.credentialService.getUserCredentials(this.userService.getUserId(username)));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("homeFiles",this.fileService.getUserFiles(this.userService.getUserId(username)));
        return "home";}
}
