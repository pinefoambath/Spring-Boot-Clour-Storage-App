package com.udacity.jwdnd.course1.cloudstorage.services;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
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

    public void insert (Credential credential) {
       credentialMapper.insert(credential);
    }

    public void update(Credential credential) {
        String url = credential.getUrl();
        String username = credential.getUserName();
        String key = credential.getKey();
        Integer userId = credential.getUserId();
        String password_plain = credential.getPassword();
        String password = encryptValue(password_plain, key);
        credentialMapper.update(url, username, password, key, userId);
    }

    public void delete(int credentialId) {
        credentialMapper.delete(credentialId);
    }

}
