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
import org.springframework.web.bind.annotation.PathVariable;
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

        if (fileService.checkFileName(fileForm)) {
            return "redirect:/result?Error" + "&errorType" + 2;
        }

        FileForm fileForm = new FileForm();
        fileForm.setContentType(fileUpload.getContentType());
        fileForm.setFileName(fileUpload.getName());
        fileForm.setFileData(ArrayUtils.toObject(fileUpload.getBytes()));
        fileForm.setFileSize(String.valueOf(fileUpload.getSize()));
        fileForm.setUserId(user.getUserId());
        fileService.addFile(fileForm);
        Boolean isSuccess = true;
        // make sure to use redirect:/, as with just "/home" the Controller wouldn't be invoked at the backend
         return "redirect:/result?isSuccess=" + isSuccess;
    }

    @GetMapping("/delete-file/{fileId}")
    public String deleteFile(@PathVariable("fileId") String fileId) {
        if (Integer.parseInt(fileId) > 0) {
            fileService.deleteFile(Integer.parseInt(fileId));
            Boolean isSuccess = true;
              return "redirect:/result?isSuccess=" + isSuccess;
        }
        Boolean isSuccess = false;
        return "redirect:/result?isSuccess=" + false + "&errorType" + 1;
    }

}


