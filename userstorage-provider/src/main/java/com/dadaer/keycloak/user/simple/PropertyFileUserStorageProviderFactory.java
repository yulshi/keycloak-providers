package com.dadaer.keycloak.user.simple;

import org.keycloak.common.util.EnvUtil;
import org.keycloak.component.ComponentModel;
import org.keycloak.component.ComponentValidationException;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;
import org.keycloak.storage.UserStorageProviderFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * describe here
 *
 * @author shiyulong
 * @since 2023/11/2
 */
public class PropertyFileUserStorageProviderFactory implements UserStorageProviderFactory<PropertyFileUserStorageProvider> {

    public static final String PROVIDER_NAME = "readonly-property-file";

    protected static final List<ProviderConfigProperty> CONFIG_METADATA;

    static {
        CONFIG_METADATA = ProviderConfigurationBuilder.create()
                .property().name("path")
                .type(ProviderConfigProperty.STRING_TYPE)
                .label("Path")
                .defaultValue("${jboss.server.config.dir}/example-users.properties")
                .helpText("File path to properties file")
                .add().build();
    }

    @Override
    public String getId() {
        return PROVIDER_NAME;
    }

    @Override
    public PropertyFileUserStorageProvider create(KeycloakSession session, ComponentModel model) {

        String path = model.getConfig().getFirst("path");

        Properties props = new Properties();
        try {
            InputStream is = new FileInputStream(path);
            props.load(is);
            is.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new PropertyFileUserStorageProvider(session, model, props);
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return CONFIG_METADATA;
    }

    @Override
    public void validateConfiguration(KeycloakSession session, RealmModel realm, ComponentModel config)
            throws ComponentValidationException {
        String fp = config.getConfig().getFirst("path");
        if (fp == null) {
            throw new ComponentValidationException("user property file does not exist");
        }
        fp = EnvUtil.replace(fp);
        File file = new File(fp);
        if (!file.exists()) {
            throw new ComponentValidationException("user property file does not exist");
        }
    }
}
