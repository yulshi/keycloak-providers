package com.dadaer.keycloak.example.rest;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.services.resource.RealmResourceProviderFactory;

/**
 * describe here
 *
 * @author shiyulong
 * @since 2023/10/31
 */
public class MyRestProviderFactory implements RealmResourceProviderFactory {

    private final static String PROVIDER_ID = "example-rest";

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public RealmResourceProvider create(KeycloakSession session) {
        return new MyRestProvider(session);
    }

    /**
     * Only called once when the factory is first created.  This config is pulled from keycloak_server.json
     */
    @Override
    public void init(Config.Scope config) {

    }

    /**
     * Called after all provider factories have been initialized
     */
    @Override
    public void postInit(KeycloakSessionFactory factory) {

    }

    /**
     * This is called when the server shuts down.
     */
    @Override
    public void close() {

    }


}
