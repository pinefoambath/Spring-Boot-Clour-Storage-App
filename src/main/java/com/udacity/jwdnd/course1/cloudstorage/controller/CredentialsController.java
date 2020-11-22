package com.udacity.jwdnd.course1.cloudstorage.controller;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpSession;

@Controller
public class CredentialsController {

    private NoteService noteService;
    private EncryptionService encryptionService;
    private FileService fileService;
    private UserService userService;
    private CredentialForm credentialForm;
    private Credential credential;

    @Autowired
    CredentialService credentialService;

    @Autowired
    UserMapper userMapper;

//    public CredentialsController(CredentialService credentialService, NoteService noteService, EncryptionService encryptionService, FileService fileService, UserService userService) {
//        this.credentialService = credentialService;
//        this.noteService = noteService;
//        this.encryptionService = encryptionService;
//        this.fileService = fileService;
//        this.userService = userService;
//        this.credentialForm = credentialForm;
//        this.credential = credential;
//    }

    // VIEW or EDIT
        @PostMapping("/credentials")
        public String update(Authentication authentication, CredentialForm credentialForm, Model model, HttpSession session) throws Exception {
            String username = authentication.getName();
            User user = userMapper.getUser(username);
            Integer userId = user.getUserId();
            credentialForm.setUserId(userId);
        //check if there are any credentials to update, otherwise insert new credentials
        if(this.credentialService.getCredentialsByUserId(userId) == null) {
            this.credentialService.insert(credential);
        } else {
            this.credentialService.update(credential);
        }

        return "home";
    }

    //DELETE credentials
    @GetMapping("/credentials/delete")
    public String delete(int credentialId) {
        if (credentialId > 0) {
            credentialService.delete(credentialId);
            return "home";
        }
        return "/credentials";
    }

}
