package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FilesController {

    @Autowired
    private FileService fileService;
    private AuthenticationService authenticationService;
    private FileForm fileForm;
    @Autowired
    private UserMapper userMapper;
    @PostMapping("/files")
    public String insertFile(Authentication authentication, MultipartFile fileUpload) throws Exception {
        String username = authentication.getName();
        User user = userMapper.getUser(username);
        if (fileUpload.isEmpty()) {
            return "redirect:/result?error";
        }
        FileForm fileForm = new FileForm();
        fileForm.setContentType(fileUpload.getContentType());
        fileForm.setFileName(fileUpload.getName());
        fileForm.setFileData(ArrayUtils.toObject(fileUpload.getBytes()));
        fileForm.setFileSize(String.valueOf(fileUpload.getSize()));
        fileForm.setUserId(user.getUserId());
        fileService.addFile(fileForm);
        return "success";
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


