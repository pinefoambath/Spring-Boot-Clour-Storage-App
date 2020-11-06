package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FilesController {

    private FileService fileService;
    private AuthenticationService authenticationService;
    private FileForm fileForm;

    @PostMapping("/files")
    public String insertFile(AuthenticationService authenticationService, MultipartFile fileUpload, Authentication auth) throws Exception {
        User user = (User) authenticationService.authenticate(auth);
        if (fileUpload.isEmpty()) {
            return "redirect:/result?error";
        }
        fileService.addFile(fileForm);
        return "redirect:/result?success";
    }

    @GetMapping("/files/delete")
    public String deleteFile(int fileId) {
        if (fileId > 0) {
            fileService.deleteFile(fileId);
            return "redirect:/result?success";
        }
        return "redirect:/result?error";
    }
}

