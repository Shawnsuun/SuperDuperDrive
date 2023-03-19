package com.udacity.jwdnd.course1.cloudstorage.model;

public class File {
    private Integer fileId;
    private String fileName;
    private Integer userId;
    private String contentType;
    private String fileSize;
    private byte[] fileData;

    public File(Integer fileId, String fileName, Integer userId, String contentType, String fileSize, byte[] fileData) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.userId = userId;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.fileData = fileData;
    }

    public int getFileId() {
        return fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public int getUserId() {
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
