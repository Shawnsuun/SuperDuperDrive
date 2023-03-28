package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for receiving requests.
 * This class needs to be a Spring Component so that Spring can
 * automatically create instances of it to receive web requests. We use
 * the @Controller annotation variation of @Component for this purpose.
 */

@Controller
@RequestMapping
public class CredentialController {
    private CredentialService credentialService;
    private UserService userService;

    private EncryptionService encryptionService;

    public CredentialController(CredentialService credentialService, UserService userService, EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/updateCredential")
    public String addOrUpdateCredential(@ModelAttribute("credential") Credential credential,
                                        Authentication authentication,
                                        EncryptionService encryptionService,
                                        Model model) {
        Integer userId = userService.getUserId(authentication.getName());
        credential.setUserId(userId);
        credential.setKey(userService.getUser(authentication.getName()).getSalt());
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), userService.getUser(authentication.getName()).getSalt()));
        if (credential.getCredentialId() == null) {
            credentialService.addCredential(credential);
        } else {
            credentialService.update(credential);
        }
        model.addAttribute("credentials", credentialService.getUserCredentials(userId));
        model.addAttribute("success", true);
        return "result";
    }

    @GetMapping("/deleteCredential")
    public String deleteCredential(@RequestParam("credentialId") Integer credentialId, Authentication authentication, Model model) {
        String userName = authentication.getName();
        Integer userId = userService.getUserId(userName);

        // Delete the credential from the database
        credentialService.delete(credentialId);

        // Add the updated list of credentials to the model
        model.addAttribute("credentials", credentialService.getUserCredentials(userId));
        model.addAttribute("success", true);
        return "result";
    }
}
