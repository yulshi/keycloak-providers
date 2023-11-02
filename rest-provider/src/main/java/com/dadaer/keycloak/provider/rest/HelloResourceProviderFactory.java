package com.dadaer.keycloak.provider.rest;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ServerInfoAwareProviderFactory;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.services.resource.RealmResourceProviderFactory;

import java.util.Map;

/**
 * @author shiyulong
 */
public class HelloResourceProviderFactory implements RealmResourceProviderFactory, ServerInfoAwareProviderFactory {

    private final static String PROVIDER_ID = "hello";

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public RealmResourceProvider create(KeycloakSession keycloakSession) {
        return new HelloResourceProvider(keycloakSession);
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

    @Override
    public Map<String, String> getOperationalInfo() {
        return Map.of("description", "This rest api is meant for testing purpose only");
    }
}