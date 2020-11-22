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

    // leave the return method as void here; we will use Note and directly hand Note to the NoteMapper insert method
    public void insertNote (Note note) {
        noteMapper.insert(note);
    }

    public void updateNote(Note note) {
        String noteTitle = note.getNoteTitle();
        String noteDescription = note.getNoteDescription();
        Integer noteId = note.getNoteId();

        noteMapper.update(noteTitle, noteDescription, noteId);
    }

    public Boolean deleteNote(int noteId) {
        this.noteMapper.delete(noteId);
        return true;
    }

}