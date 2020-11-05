package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;
    private NoteForm noteForm;

    public NoteService(NoteMapper noteMapper, NoteForm noteForm) {
        this.noteMapper = noteMapper;
        this.noteForm = noteForm;
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