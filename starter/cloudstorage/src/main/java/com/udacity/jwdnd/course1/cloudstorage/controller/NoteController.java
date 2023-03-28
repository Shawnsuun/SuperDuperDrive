package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 * Controller for receiving requests.
 * This class needs to be a Spring Component so that Spring can
 * automatically create instances of it to receive web requests. We use
 * the @Controller annotation variation of @Component for this purpose.
 */
@Controller
@RequestMapping     //@RequestMapping is an annotation that maps HTTP requests to controller methods.
public class NoteController {
    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    /**
     * This is a Java method that maps to a POST request for updating a note in a web application.
     *
     * The @ModelAttribute("note") annotation is used to bind the Note object to the form data submitted in the request.
     *
     * The Authentication object is used to retrieve the username of the authenticated user, which is then used to
     * retrieve the user's ID from the UserService.
     *
     * The user ID is set on the Note object.
     *
     * The method checks whether the note has an ID or not. If it doesn't, it means that it's a new note and the addNote
     * method of the NoteService is called to save the note to the database. If it does have an ID, it means that it's
     * an existing note and the update method of the NoteService is called to update the note in the database.
     *
     * The Model object is used to add two attributes: "success" and "notes". The "success" attribute is set to true to
     * indicate that the update was successful.
     * The "notes" attribute is set to the list of the user's notes retrieved from the NoteService.
     *
     * Finally, the method returns a string "result", which is the name of the view that should be rendered to display
     * the result of the update operation.
     */
    @PostMapping("/updateNote")
    public String addOrUpdateNote(@ModelAttribute("note") Note note, Authentication authentication, Model model) {
        Integer userId = userService.getUserId(authentication.getName());
        note.setUserId(userId);
        if (note.getNoteId() == null) {
            noteService.addNote(note);
        } else {
            noteService.update(note);
        }
        model.addAttribute("success", true);
        model.addAttribute("notes", noteService.getUserNotes(userId));

        return "result";
    }

    /**
     * This is a Java method that maps to a GET request for deleting a note in a web application.
     *
     * The @RequestParam("noteId") annotation is used to bind the noteId parameter to the value of the "noteId" query
     * parameter in the request URL(home.html).
     *
     * The Authentication object is used to retrieve the username of the authenticated user, which is then used to
     * retrieve the user's ID from the UserService.
     *
     * The note with the given ID is deleted from the database using the delete method of the NoteService.
     *
     * The Model object is used to add two attributes: "success" and "notes". The "success" attribute is set to true
     * to indicate that the deletion was successful. The "notes" attribute is set to the list of the user's notes
     * retrieved from the NoteService.
     *
     * Finally, the method returns a string "result", which is the name of the view that should be rendered to display
     * the result of the deletion operation.
     */
    @GetMapping("/deleteNote")
    public String deleteNote(@RequestParam("noteId") Integer noteId, Authentication authentication, Model model) {
        String userName = authentication.getName();
        Integer userId = userService.getUserId(userName);

        // Delete the note from the database
        noteService.delete(noteId);

        // Add the updated list of notes to the model
        model.addAttribute("notes", noteService.getUserNotes(userId));
        model.addAttribute("success", true);
        return "result";
    }
}
