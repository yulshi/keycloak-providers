package com.dadaer.keycloak.example.impl;

import com.dadaer.keycloak.example.spi.ExampleServiceProvider;
import org.keycloak.models.KeycloakSession;

/**
 * describe here
 *
 * @author shiyulong
 * @since 2023/10/31
 */
public class MyExampleServiceProvider implements ExampleServiceProvider {

    private final KeycloakSession session;

    public MyExampleServiceProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public String sayHi(String name) {
        return "Hello, " + name + " from " + session.getContext().getRealm().getName();
    }

    @Override
    public void close() {

    }
}
