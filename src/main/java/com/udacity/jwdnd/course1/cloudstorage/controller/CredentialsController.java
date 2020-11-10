package com.udacity.jwdnd.course1.cloudstorage.controller;
import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpSession;


public class CredentialsController {

    private CredentialService credentialService;
    private NoteService noteService;
    private EncryptionService encryptionService;
    private FileService fileService;
    private UserService userService;
    private CredentialForm credentialForm;

    public CredentialsController(CredentialService credentialService, NoteService noteService, EncryptionService encryptionService, FileService fileService, UserService userService) {
        this.credentialService = credentialService;
        this.noteService = noteService;
        this.encryptionService = encryptionService;
        this.fileService = fileService;
        this.userService = userService;
        this.credentialForm = credentialForm;
    }

    // VIEW or EDIT
        @PostMapping("/credentials")
        public String update(Authentication authentication, CredentialForm credentialForm, Model model, HttpSession session) {
            User user = (User) session.getAttribute("loggeduser");
            model.addAttribute("User", user);
            Integer userId = user.getUserId();
        //check if there are any credentials to update
        if(this.credentialService.getCredentialsByUserId(userService.getUser(userId))){
            this.credentialService.update(credentialForm, userId);
        } else {
            this.credentialService.insert(this.credential, userId);
        }

        getCredentialDetails(authentication, model);

        return "home";
    }

    //delete credentials
    @GetMapping("/credentials/delete")
    public String delete(int credentialId) {
        if (credentialId > 0) {
            credentialService.delete(credentialId);
            return "home";
        }
        return "/credentials";
    }

}
