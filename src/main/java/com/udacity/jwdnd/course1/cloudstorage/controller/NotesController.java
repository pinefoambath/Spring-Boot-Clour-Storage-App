package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class NotesController {
    private Note note;
    private UserService userService;
    private NoteForm noteForm;

    @Autowired
    NoteService noteService;

    @PostMapping("/notes")
    public String update(Authentication authentication, NoteForm noteForm, Model model, HttpSession session) throws Exception {
        User user = (User) session.getAttribute("loggeduser");
        model.addAttribute("User", user);
        Integer userId = user.getUserId();
        //check if there are any notes to update, otherwise insert new note
        if(noteForm.getNoteId() == null) {
            this.noteService.insertNote(noteForm);
        } else {
            // Set the attributes of note here e.g
//            note.setTitle(noteForm.title);
            this.noteService.updateNote(note);
        }
        return "/home";
    }
    @GetMapping("/notes/delete")
    public String deleteNote(int noteId) {
        if (noteId > 0) {
            noteService.deleteNote(noteId);
            return "/home";
        }
        return "/notes";
    }
}



