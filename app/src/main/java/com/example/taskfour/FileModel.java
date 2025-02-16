package com.example.taskfour;

public class FileModel {
    private String fileName, fileUrl, uploadTime;

    public FileModel() {
        // Empty constructor required for Firebase
    }

    public FileModel(String fileName, String fileUrl, String uploadTime) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.uploadTime = uploadTime;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getUploadTime() {
        return uploadTime;
    }
}

