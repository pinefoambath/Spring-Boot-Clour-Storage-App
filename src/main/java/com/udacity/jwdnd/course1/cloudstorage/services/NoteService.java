package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;
    // the below was wrong; as I instantiated it in here we missed that actually we're working with a NoteForm from the th:object, so n
    // private NoteForm noteForm;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getAllNotes(int userId) throws Exception {
        List<Note> notes = noteMapper.getNoteByUserId(userId);
        if (notes == null) {
            throw new Exception();
        }
        return notes;
    }

    public Boolean insertNote(NoteForm noteForm) {
        String noteTitle = noteForm.getNoteTitle();
        String noteDescription = noteForm.getNoteDescription();
        Integer userId = noteForm.getUserId();
        this.noteMapper.insert(noteTitle, noteDescription, userId);
        return true;
    }

    public Boolean updateNote(Note note) {

        String noteTitle = note.getNoteTitle();
        String noteDescription = note.getNoteDescription();
        Integer noteId = note.getNoteId();
        this.noteMapper.update(noteTitle, noteDescription, noteId);
        return true;
    }

    public Boolean deleteNote(int noteId) {
        this.noteMapper.delete(noteId);
        return true;
    }

}