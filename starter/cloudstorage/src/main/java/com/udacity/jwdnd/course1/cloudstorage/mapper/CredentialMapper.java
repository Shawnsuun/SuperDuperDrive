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
    Credential getCredential(Integer credentialId);

    @Select("SELECT * FROM CREDENTIALS")
    List<Credential> getAllCredentials();

    @Insert("INSERT INTO NOTES(credentialId, username, password, url, userId, key) VALUES (" +
            "#{credentialId}, #{username}, #{password}, #{url}, #{userId}, #{key})")

    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    Integer addCredential(Credential credential);

    @Update("UPDATE credentials " +
            "SET username=#{username}, " +
            "password=#{password}, " +
            "url=#{url} " +
            "WHERE credentialId = #{credentialId}")
    Integer update(Credential credential);

    @Delete("DELETE FROM NOTES WHERE credentialId = #{credentialId}")
    Integer delete(Integer credentialId);
}
