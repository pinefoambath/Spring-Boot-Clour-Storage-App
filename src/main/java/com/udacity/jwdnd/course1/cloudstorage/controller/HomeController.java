package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.*;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
// telling it that this controller oversees everything in the /home folder
@RequestMapping("/home")
public class HomeController {

    private final UserService userService;
    private final NoteService noteService;
    private final FileService fileService;

    public HomeController(UserService userService, NoteService noteService, FileService fileService) {
        this.userService = userService;
        this.noteService = noteService;
        this.fileService = fileService;
    }

    @GetMapping
    // we declare a NoteForm object, which allows the app to initialise a POJO for the Noteform backend
    public String getNoteForm (NoteForm noteForm, Model model) throws Exception {
        model.addAttribute("Notes", this.noteService.getAllNotes(noteForm.getUserId()));
        return "home";
    }
}
