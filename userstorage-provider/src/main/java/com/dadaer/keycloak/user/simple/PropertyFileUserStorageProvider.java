package com.dadaer.keycloak.user.simple;

import org.keycloak.common.util.EnvUtil;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputUpdater;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.*;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;
import org.keycloak.storage.user.UserRegistrationProvider;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * describe here
 *
 * @author shiyulong
 * @since 2023/11/2
 */
public class PropertyFileUserStorageProvider implements
        UserStorageProvider,
        UserLookupProvider,
        CredentialInputValidator,
        CredentialInputUpdater,
        UserQueryProvider,
        UserRegistrationProvider {

    protected KeycloakSession session;
    protected final Properties properties;
    protected ComponentModel model;

    protected Map<String, UserModel> loadedUsers = new HashMap<>();

    public static final String UNSET_PASSWORD = "#$!-UNSET-PASSWORD";

    public PropertyFileUserStorageProvider(KeycloakSession session, ComponentModel model, Properties properties) {
        this.session = session;
        this.properties = properties;
        this.model = model;
    }

    //////////////////////////////////////////////////////////////////////////
    // Methods defined in UserLookupProvider
    //////////////////////////////////////////////////////////////////////////
    @Override
    public UserModel getUserByUsername(RealmModel realm, String username) {
        UserModel cachedUser = loadedUsers.get(username);
        if (cachedUser == null) {
            String password = properties.getProperty(username);
            if (password != null) {
                cachedUser = createAdapter(realm, username);
                loadedUsers.put(username, cachedUser);
            }
        }
        return cachedUser;
    }

    @Override
    public UserModel getUserById(RealmModel realm, String id) {
        StorageId storageId = new StorageId(id);
        String username = storageId.getExternalId();
        return getUserByUsername(realm, username);
    }

    @Override
    public UserModel getUserByEmail(RealmModel realm, String email) {
        return null;
    }

    protected UserModel createAdapter(RealmModel realm, String username) {
        //return new NormalUserModel(session, realm, model, username);
        return new FederatedUserModel(session, realm, model, username, properties, this::save);
    }

    ////////////////////////////////////////////////////////////////////////
    // Methods defined in CredentialInputValidator
    ////////////////////////////////////////////////////////////////////////
    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
        String password = properties.getProperty(user.getUsername());
        return credentialType.equals(PasswordCredentialModel.TYPE) && password != null;
    }

    @Override
    public boolean isValid(RealmModel realm, UserModel user, CredentialInput input) {
        if (!supportsCredentialType(input.getType()) || !(input instanceof UserCredentialModel cred)) {
            return false;
        }

        String password = properties.getProperty(user.getUsername());
        if (password == null || UNSET_PASSWORD.equals(password)) {
            return false;
        }
        return password.equals(cred.getValue());
    }

    @Override
    public boolean supportsCredentialType(String credentialType) {
        return credentialType.equals(PasswordCredentialModel.TYPE);
    }

    ////////////////////////////////////////////////////////////////////////
    // Methods defined in CredentialInputUpdater
    ////////////////////////////////////////////////////////////////////////

    @Override
    public boolean updateCredential(RealmModel realm, UserModel user, CredentialInput input) {
        System.out.println("---> updateCredential: " + user.getUsername() + ", " + input.getChallengeResponse());
        if (!(input instanceof UserCredentialModel cred)) {
            System.out.println("---> updateCredential: not instance of UserCredentialModel");
            return false;
        }
        if (!input.getType().equals(PasswordCredentialModel.TYPE)) {
            System.out.println("---> updateCredential: type not match - " + input.getType());
            return false;
        }
        synchronized (properties) {
            properties.setProperty(user.getUsername(), cred.getValue());
            save();
        }
        System.out.println("---> updateCredential: updated");
        return true;
    }

    @Override
    public void disableCredentialType(RealmModel realm, UserModel user, String credentialType) {
        if (!credentialType.equals(PasswordCredentialModel.TYPE)) {
            return;
        }
        synchronized (properties) {
            properties.setProperty(user.getUsername(), UNSET_PASSWORD);
            save();
        }
    }

    @Override
    public Stream<String> getDisableableCredentialTypesStream(RealmModel realm, UserModel user) {
        return Stream.of(PasswordCredentialModel.TYPE);
    }

    @Override
    public void close() {
    }

    ////////////////////////////////////////////////////////////////////////
    // Methods defined in UserQueryProvider
    ////////////////////////////////////////////////////////////////////////
    @Override
    public Stream<UserModel> searchForUserStream(RealmModel realm, Map<String, String> params, Integer firstResult, Integer maxResults) {

        String search = params.get(UserModel.SEARCH);
        System.out.println("---> search=" + search);
        Predicate<String> predicate = search == null || "*".equals(search) || search.isEmpty()
                ? username -> true
                : username -> username.contains(search);
        return properties.keySet().stream()
                .map(String.class::cast)
                .filter(predicate)
                .skip(firstResult)
                .map(username -> getUserByUsername(realm, username))
                .limit(maxResults);
    }

    @Override
    public Stream<UserModel> getGroupMembersStream(RealmModel realm, GroupModel group, Integer firstResult, Integer maxResults) {
        return Stream.empty();
    }

    @Override
    public Stream<UserModel> searchForUserByUserAttributeStream(RealmModel realm, String attrName, String attrValue) {
        return Stream.empty();
    }

    @Override
    public int getUsersCount(RealmModel realm) {
        return properties.size();
    }

    ////////////////////////////////////////////////////////////////////////
    // Methods defined in UserRegistrationProvider
    ////////////////////////////////////////////////////////////////////////
    @Override
    public UserModel addUser(RealmModel realm, String username) {
        synchronized (properties) {
            properties.setProperty(username, UNSET_PASSWORD);
            save();
        }
        return createAdapter(realm, username);
    }

    @Override
    public boolean removeUser(RealmModel realm, UserModel user) {
        synchronized (properties) {
            if (properties.remove(user.getUsername()) == null) {
                return false;
            }
            save();
            return true;
        }
    }

    public void save() {
        String path = model.getConfig().getFirst("path");
        path = EnvUtil.replace(path);
        try {
            FileOutputStream fos = new FileOutputStream(path);
            properties.store(fos, "");
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
