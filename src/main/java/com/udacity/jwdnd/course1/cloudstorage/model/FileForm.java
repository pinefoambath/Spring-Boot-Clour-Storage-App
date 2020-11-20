package com.udacity.jwdnd.course1.cloudstorage.model;


public class FileForm {
    private Integer fileId;
    private String fileName;
    private String contentType;
    private String fileSize;
    private Integer userId;
    private Byte[] fileData;

    public FileForm() {}

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Byte[] getFileData() {
        return fileData;
    }

    public void setFileData(Byte[] fileData) {
        this.fileData = fileData;
    }
}