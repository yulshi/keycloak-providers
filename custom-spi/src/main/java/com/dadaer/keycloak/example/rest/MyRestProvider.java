package com.dadaer.keycloak.example.rest;

import com.dadaer.keycloak.example.spi.ExampleServiceProvider;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

/**
 * describe here
 *
 * @author shiyulong
 * @since 2023/10/31
 */
public class MyRestProvider implements RealmResourceProvider {

    private final KeycloakSession session;

    public MyRestProvider(KeycloakSession session) {
        this.session = session;
    }

    /**
     * <p>Returns a JAX-RS resource instance.
     *
     * @return a JAX-RS sub-resource instance
     */
    @Override
    public Object getResource() {
        return this;
    }

    @Override
    public void close() {

    }

    @GET
    @Path("")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHi(@QueryParam("name") String name) {
        ExampleServiceProvider example = session.getProvider(ExampleServiceProvider.class);
        return example.sayHi(name);
    }

}
