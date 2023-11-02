package com.dadaer.keycloak.user.simple;

import org.keycloak.component.ComponentModel;
import org.keycloak.credential.LegacyUserCredentialManager;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.SubjectCredentialManager;
import org.keycloak.storage.adapter.AbstractUserAdapter;

import java.util.List;

/**
 * describe here
 *
 * @author shiyulong
 * @since 2023/11/2
 */
public class NormalUserModel extends AbstractUserAdapter {

    private final String username;

    public NormalUserModel(KeycloakSession session, RealmModel realm, ComponentModel model, String username) {
        super(session, realm, model);
        this.username = username;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public SubjectCredentialManager credentialManager() {
        return new LegacyUserCredentialManager(session, realm, this);
    }

    @Override
    public void addRequiredAction(String action) {
    }

    @Override
    public void removeRequiredAction(String action) {
    }

    @Override
    public void addRequiredAction(RequiredAction action) {
    }

    @Override
    public void removeRequiredAction(RequiredAction action) {
    }

    @Override
    public void setCreatedTimestamp(Long timestamp) {
    }

    @Override
    public void setSingleAttribute(String name, String value) {
    }

    @Override
    public void removeAttribute(String name) {
    }

    @Override
    public void setAttribute(String name, List<String> values) {
    }

    @Override
    public void setUsername(String username) {
    }

    @Override
    public void setEnabled(boolean enabled) {
    }

}
