package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userId = #{userId}")
    List<Note> getUserNotes(Integer userId);

    @Select("SELECT FROM NOTES WHERE noteId = #{noteId}")
    Note getNote(Integer noteId);

    @Insert("INSERT INTO NOTES(noteId, username, title, description, userId) VALUES (" +
            "#{noteId}, #{username}, #{title}, #{description}, #{userId})")

    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int addNote(Note chatMessage);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    void delete(Integer noteId);
}
