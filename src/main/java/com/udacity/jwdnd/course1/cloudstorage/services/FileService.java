package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileService {

    private FileMapper fileMapper;
    private FileForm fileForm;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
        this.fileForm = fileForm;
    }

    public List<File> getAllFiles(int userId) throws Exception {
        List<File> files = fileMapper.getFileByUserId(userId);
        if (files == null) {
            throw new Exception();
        }
        return files;
    }

    public Boolean checkFileName(FileForm fileForm) throws Exception {
        Integer userId = fileForm.getUserId();
        List<File> files = this.getAllFiles(userId);
        String fileName = fileForm.getFileName();
        if (files.contains(fileName)) {
            return true;
        }
            return false;
    }

    public Boolean addFile(FileForm fileForm) {
        String fileName = fileForm.getFileName();
        String contentType = fileForm.getContentType();
        Integer userId = fileForm.getUserId();
        Byte[] fileData = fileForm.getFileData();
        String fileSize = fileForm.getFileSize();
        File file = new File(fileName, contentType, fileSize, userId, fileData);
        this.fileMapper.addFile(file);
        return true;
    }


        public Boolean deleteFile(int fileId) {
        this.fileMapper.delete(fileId);
        return true;
    }

}