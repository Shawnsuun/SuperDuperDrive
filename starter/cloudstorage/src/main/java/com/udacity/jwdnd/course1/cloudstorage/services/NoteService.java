package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating NoteService bean");
    }

    public List<Note> getUserNotes(Integer userId) {
        return noteMapper.getUserNotes(userId);
    }

    Note getNote(Integer noteId) {
        return noteMapper.getNote(noteId);
    }

    Integer addNote(Note note) {
        return noteMapper.addNote(note);
    }

    Integer update(Note note) {
        return noteMapper.update(note);
    }

    Integer delete(Integer noteId) {
        return noteMapper.delete(noteId);
    }
}
