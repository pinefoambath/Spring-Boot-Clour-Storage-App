package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class NotesController {
    private Note note;
    private UserService userService;
    private NoteForm noteForm;

    // inject the NoteService through AutoWired
    // will also use the UserMapper to get the current User and then insert the note for that specific user
    // will also create a Note object and pass the values from NoteForm into it
    // we will also pass the Model the credentialForm and list of notes so that we don't receive a BindingResult error
    @Autowired
    NoteService noteService;

    @Autowired
    UserMapper userMapper;

    @PostMapping("/notes")
    public String update(Authentication authentication, NoteForm noteForm, Model model, HttpSession session) throws Exception {
        String username = authentication.getName();
        User user = userMapper.getUser(username);
        model.addAttribute("user", user);
        noteForm.setUserId(user.getUserId());
        System.out.println("NOTE ID: " + noteForm.getNoteId());
        Note note = new Note(noteForm.getNoteId(), noteForm.getNoteTitle(),noteForm.getNoteDescription(), user.getUserId());
        //check if there are any notes to update, otherwise insert new note
        if(note.getNoteId() == 0) {
            this.noteService.insertNote(note);
        } else {
            // Set the attributes of note here e.g
//            note.setTitle(noteForm.title);
            this.noteService.updateNote(note);
        }
        model.addAttribute("credentialForm",new CredentialForm());
        List<Note> notes = noteService.getAllNotes(user.getUserId());
        model.addAttribute("notes",notes);
        return "/home";
    }

    @GetMapping("/delete-note/{noteId}")
    public String deleteNote(@PathVariable("noteId") String noteId) {
        if (Integer.parseInt(noteId) > 0) {
            noteService.deleteNote(Integer.parseInt(noteId));
            return "redirect:/home";
        }
        return "redirect:/home";
    }


}



