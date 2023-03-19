package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId}")
    List<Credential> getUserCredentials(Integer userId);

    @Select("SELECT FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    Note getCredential(Integer credentialId);

    @Select("SELECT * FROM CREDENTIALS")
    List<Credential> getAllCredentials();

    @Insert("INSERT INTO NOTES(credentialId, username, password, url, userId, key) VALUES (" +
            "#{credentialId}, #{username}, #{password}, #{url}, #{userId}, #{key})")

    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int addCredential(Note chatMessage);

    @Delete("DELETE FROM NOTES WHERE credentialId = #{credentialId}")
    void delete(Integer credentialId);
}
