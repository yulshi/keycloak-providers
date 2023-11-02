package com.dadaer.keycloak.jpa;

import com.dadaer.keycloak.jpa.entity.Company;
import org.keycloak.connections.jpa.entityprovider.JpaEntityProvider;
import org.keycloak.models.KeycloakSession;

import java.util.Collections;
import java.util.List;

/**
 * describe here
 *
 * @author shiyulong
 * @since 2023/10/31
 */
public class CompanyJapEntityProvider implements JpaEntityProvider {

    private final KeycloakSession session;

    public CompanyJapEntityProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public List<Class<?>> getEntities() {
        return Collections.singletonList(Company.class);
    }

    @Override
    public String getChangelogLocation() {
        return "example-changelog.xml";
    }

    @Override
    public String getFactoryId() {
        return CompanyJpaEntityProviderFactory.PROVIDER_ID;
    }

    @Override
    public void close() {

    }
}
