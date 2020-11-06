package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    private Credential encryptValue(Credential credential) {
        String key = StringUtils.random(16, true, true);
        credential.setKey(key);
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), key));
        return credential;
    }

    private Credential decryptValue(Credential credential) {
        credential.setPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
        return credential;
    }

    public List<Credential> getCredentialsByUserId(int userId) throws Exception {
        List<Credential> credentials = credentialMapper.getCredentialsByUserId(userId);
        if (credentials == null) {
            throw new Exception();
        }
        return credentials;
    }

    public void insert(Credential credential, int userId) {
        credentialMapper.insert(encryptValue(credential), userId);
    }

    public void update(Credential credential, int userId) {
        credentialMapper.update(encryptValue(credential), userId);
    }

    public void delete(int credentialId) {
        credentialMapper.delete(credentialId);
    }

}