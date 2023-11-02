package com.dadaer.keycloak.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.cache.NoCache;
import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.utils.KeycloakModelUtils;
import org.keycloak.services.resource.RealmResourceProvider;
import jakarta.persistence.EntityManager;
import com.dadaer.keycloak.jpa.entity.Company;

import java.util.LinkedList;
import java.util.List;

/**
 * describe here
 *
 * @author shiyulong
 * @since 2023/10/31
 */
public class CompanyRestProvider implements RealmResourceProvider {

    private final KeycloakSession session;

    public CompanyRestProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public Object getResource() {
        return this;
    }

    @Override
    public void close() {
    }

    private EntityManager getEntityManager() {
        return session.getProvider(JpaConnectionProvider.class).getEntityManager();
    }

    protected RealmModel getRealm() {
        return session.getContext().getRealm();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CompanyRepresentation addCompany(CompanyRepresentation companyRep) {

        Company entity = new Company();
        String id = companyRep.getId() == null ? KeycloakModelUtils.generateId() : companyRep.getId();
        entity.setId(id);
        entity.setName(companyRep.getName());
        entity.setRealmId(getRealm().getId());
        getEntityManager().persist(entity);

        companyRep.setId(id);
        return companyRep;
    }

    @GET
    @Path("")
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public List<CompanyRepresentation> getCompanies() {

        List<Company> companyEntities = getEntityManager()
                .createNamedQuery("findByRealm", Company.class)
                .setParameter("realmId", getRealm().getId())
                .getResultList();

        List<CompanyRepresentation> result = new LinkedList<>();
        for (Company entity : companyEntities) {
            result.add(new CompanyRepresentation(entity));
        }

        return result;
    }

}
