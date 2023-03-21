package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT FROM FILES WHERE fileId = #{fileId}")
    File getFile(int fileId);

    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<File> getUserFiles(int userId);

    @Select("SELECT * FROM FILES")
    List<File> getAllFiles();

    @Insert("INSERT INTO FILES (fileId, " +
            "fileName, " +
            "userId, " +
            "contentType, " +
            "fileSize, " +
            "fileData;) " +
            "VALUES(#{fileName}, " +
            "#{userId}, " +
            "#{contentType}, " +
            "#{fileSize}, " +
            "#{fileData})")

    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer addFile(File file);


    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    Integer delete(int fileId);
}
