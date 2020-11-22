package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;


@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;
    private CredentialForm credentialForm;

    public CredentialService(CredentialMapper credentialMapper)

    {

        this.credentialMapper = credentialMapper;

    }
//    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService, CredentialForm credentialForm) {
//        this.credentialMapper = credentialMapper;
//        this.encryptionService = encryptionService;
//        this.credentialForm = credentialForm;
//    }

    private Credential encryptValue(Credential credential) {
        String key = RandomStringUtils.random(16, true, true);
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

    public void insert(CredentialForm credentialForm) {
        Credential credential = new Credential();
        credential.setPassword(credentialForm.getPassword());
        credential = encryptValue(credential);
        credential.setUrl(credentialForm.getUserId());
        credential.setUserName(credentialForm.getUserName());
        credentialMapper.update(credential);
    }

    public void update(CredentialForm credentialForm) {
        Credential credential = new Credential();
        credential.setPassword(credentialForm.getPassword());
        credential = encryptValue(credential);
        credential.setUrl(credentialForm.getUserId());
        credential.setUserName(credentialForm.getUserName());
        credentialMapper.update(credential);
    }

    public void delete(int credentialId) {
        credentialMapper.delete(credentialId);
    }

}