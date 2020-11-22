package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
//mark this as a @Respository so that it can be included in ApplicationContext
//this way we can pass a Note Object in inserNote, rather than passing individual values like noteId
@Repository
@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES")
    List<Note> getAllNotes();

    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId}")
    Note getNoteById(Integer noteId);

    @Select("SELECT * FROM NOTES WHERE userId = #{userId}")
    List<Note> getNoteByUserId(Integer userId);

    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    void delete(Integer noteId);

    @Delete("DELETE FROM NOTES")
    void deleteAll();

    @Update("UPDATE NOTES " +
            "SET noteTitle = #{noteTitle}, noteDescription = #{noteDescription} " +
            "WHERE noteId = #{noteId}")
    int update(String noteTitle, String noteDescription, Integer noteId);
}
