package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import com.udacity.jwdnd.course1.cloudstorage.mapper.*;
import javax.servlet.http.HttpSession;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.util.List;

@Controller
// telling it that this controller oversees everything in the /home folder
@RequestMapping("/home")
public class HomeController {

    private final UserService userService;
    private final NoteService noteService;
    private final FileService fileService;
    private final CredentialService credentialService;
    private UserMapper userMapper;
    private AuthenticationService authenticationService;

    public HomeController(UserService userService, NoteService noteService, FileService fileService, CredentialService credentialService, UserMapper userMapper, AuthenticationService authenticationService) {
        this.userService = userService;
        this.noteService = noteService;
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.userMapper = userMapper;
        this.authenticationService = authenticationService;
    }


    @RequestMapping(value = "/home", method = RequestMethod.GET)
// need to tell it that some methods in here throw exceptions
    public String getHomePage(Model model, HttpSession session) throws Exception {

        User user = (User) session.getAttribute("loggeduser");

        model.addAttribute("User", user);

        List<File> files = fileService.getAllFiles(user.getUserId());

        List<Note> notes = noteService.getAllNotes(user.getUserId());

        List<Credential> credentials = credentialService.getCredentialsByUserId(user.getUserId());

        model.addAttribute("Files", files);

        model.addAttribute("Notes", notes);

        model.addAttribute("Credentials", credentials);

        return "home";

    }


}
