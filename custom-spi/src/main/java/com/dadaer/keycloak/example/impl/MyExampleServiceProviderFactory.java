package com.dadaer.keycloak.example.impl;

import com.dadaer.keycloak.example.spi.ExampleServiceProvider;
import com.dadaer.keycloak.example.spi.ExampleServiceProviderFactory;
import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

/**
 * describe here
 *
 * @author shiyulong
 * @since 2023/10/31
 */
public class MyExampleServiceProviderFactory implements ExampleServiceProviderFactory {

    private static final String PROVIDER_ID = "my-spi-example";

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public ExampleServiceProvider create(KeycloakSession keycloakSession) {
        return new MyExampleServiceProvider(keycloakSession);
    }

    @Override
    public void init(Config.Scope scope) {

    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    @Override
    public void close() {

    }

}
