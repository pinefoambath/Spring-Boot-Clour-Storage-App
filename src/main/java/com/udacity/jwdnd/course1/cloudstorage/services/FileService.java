package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<File> getAllFiles(int userId) throws Exception {
        List<File> files = fileMapper.getFileByUserId(userId);
        if (files == null) {
            throw new Exception();
        }
        return files;
    }

    public Boolean addFile(FileForm fileForm) {
        String fileName = fileForm.getFileName();
        String contentType = fileForm.getContentType();
        Integer userId = fileForm.getUserId();
        Byte[] fileData = fileForm.getFileData();
        String fileSize = fileForm.getFileSize();
        this.fileMapper.addFile(fileName, contentType, fileSize, userId, fileData);
        return true;
    }


        public Boolean deleteFile(int fileId) {
        this.fileMapper.delete(fileId);
        return true;
    }

}