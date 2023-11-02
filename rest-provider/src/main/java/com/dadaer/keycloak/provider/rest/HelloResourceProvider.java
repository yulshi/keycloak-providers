package com.dadaer.keycloak.provider.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

import java.util.Map;

/**
 * describe here
 *
 * @author shiyulong
 * @since 2023/10/31
 */
public class HelloResourceProvider implements RealmResourceProvider {

    private final KeycloakSession session;

    public HelloResourceProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public Object getResource() {
        return this;
    }

    @Override
    public void close() {

    }

    @GET
    @Path("/realm")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> getRealmName() {
        String realmName = session.getContext().getRealm().getName();
        if (realmName == null || realmName.trim().isEmpty()) {
            realmName = session.getContext().getRealm().getDisplayName();
        }
        return Map.of("realmName", realmName);
    }
}
