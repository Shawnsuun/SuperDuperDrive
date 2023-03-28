package com.udacity.jwdnd.course1.cloudstorage.model;

public class File {
    private Integer fileId;     //related to the field of templates
    private String fileName;
    private Integer userId;
    private String contentType;
    private String fileSize;
    private byte[] fileData;

    public File(Integer fileId, String fileName, String contentType, String fileSize, Integer userId, byte[] fileData) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.userId = userId;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.fileData = fileData;
    }

    public Integer getFileId() {
        return fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getContentType() {
        return contentType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public byte[] getFileData() {
        return fileData;
    }
}
