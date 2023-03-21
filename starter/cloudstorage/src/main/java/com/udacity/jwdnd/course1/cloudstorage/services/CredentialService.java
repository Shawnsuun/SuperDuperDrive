package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating CredentialService bean");
    }

    public List<Credential> getUserCredentials(Integer userId) {
        return credentialMapper.getUserCredentials(userId);
    }

    public Credential getCredential(Integer credentialId) {
        return credentialMapper.getCredential(credentialId);
    }

    public List<Credential> getAllCredentials() {
        return credentialMapper.getAllCredentials();
    }

    public Integer addCredential(Credential credential) {
        return credentialMapper.addCredential(credential);
    }

    Integer update(Credential credential) {
        return credentialMapper.update(credential);
    }

    Integer delete(Integer credentialId) {
        return credentialMapper.delete(credentialId);
    }
}
