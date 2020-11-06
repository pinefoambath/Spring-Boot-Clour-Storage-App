package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Controller;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FilesController {

    private FileService fileService;
    private AuthenticationService authenticationService;

    public FilesController(FileService fileService, AuthenticationService authenticationService) {
        this.fileService = fileService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/files")
    public String insertFile(AuthenticationService authenticationService, MultipartFile fileUpload) throws IOException {
        User user = (User) authenticationService.getPrincipal();
        if (fileUpload.isEmpty()) {
            return "redirect:/result?error";
        }
        fileService.addFile(fileUpload, user.loadUserByUsername());
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


