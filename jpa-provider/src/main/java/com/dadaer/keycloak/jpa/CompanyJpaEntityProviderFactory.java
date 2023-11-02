package com.dadaer.keycloak.jpa;

import org.keycloak.Config;
import org.keycloak.connections.jpa.entityprovider.JpaEntityProvider;
import org.keycloak.connections.jpa.entityprovider.JpaEntityProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

/**
 * describe here
 *
 * @author shiyulong
 * @since 2023/10/31
 */
public class CompanyJpaEntityProviderFactory implements JpaEntityProviderFactory {

    final static String PROVIDER_ID = "company-jpa-provider";

    @Override
    public JpaEntityProvider create(KeycloakSession session) {
        return new CompanyJapEntityProvider(session);
    }

    @Override
    public void init(Config.Scope config) {

    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }
}
