package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class FilesController {

    @Autowired
    private FileService fileService;
    private AuthenticationService authenticationService;
    private FileForm fileForm;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/files")
    public String insertFile(Authentication authentication, @ModelAttribute("newfile") FileForm newFile, MultipartFile fileUpload) throws Exception {
        String username = authentication.getName();
        User user = userMapper.getUser(username);

        if (fileUpload.isEmpty()) {
            return "redirect:/home/result?error";
        }

        Integer userId = user.getUserId();
        List<File> files = fileService.getAllFiles(user.getUserId());


        String fileName = fileUpload.getOriginalFilename();
        boolean fileIsDuplicate = false;
        for (String file: files) {
            if (file.equals(fileName)) {
                fileIsDuplicate = true;

                break;
            }
        }

        fileService.addFile(fileUpload, username);
        Boolean isSuccess = true;
        // make sure to use redirect:/, as with just "/home" the Controller wouldn't be invoked at the backend
         return "redirect:/home/result?isSuccess=" + isSuccess;
    }

    @GetMapping("/delete-file/{fileId}")
    public String deleteFile(@PathVariable("fileId") int fileId) {
        if (fileId > 0) {
            fileService.deleteFile(fileId);
            Boolean isSuccess = true;
            return "redirect:/home/result?isSuccess=" + isSuccess;
        }
        Boolean isSuccess = false;
        return "redirect:/home/result?isSuccess=" + false + "&errorType" + 1;
    }

    public @ResponseBody
    byte[] getFile(@PathVariable String fileName) {
        return fileService.getFile(fileName).getFileData();
    }
}


