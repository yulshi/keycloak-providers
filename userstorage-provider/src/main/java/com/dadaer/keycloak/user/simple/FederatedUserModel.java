package com.dadaer.keycloak.user.simple;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;

import java.util.Properties;

/**
 * describe here
 *
 * @author shiyulong
 * @since 2023/11/2
 */
public class FederatedUserModel extends AbstractUserAdapterFederatedStorage {

    private final String username;
    private final Properties properties;
    private final Runnable saveFunc;

    public FederatedUserModel(KeycloakSession session,
                              RealmModel realm,
                              ComponentModel storageProviderModel,
                              String username,
                              Properties properties,
                              Runnable saveFunc) {
        super(session, realm, storageProviderModel);
        this.username = username;
        this.properties = properties;
        this.saveFunc = saveFunc;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        String pw = (String) properties.remove(username);
        if (pw != null) {
            properties.put(username, pw);
            saveFunc.run();
        }
    }

}
