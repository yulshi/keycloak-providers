package com.dadaer.keycloak.rest;

import com.dadaer.keycloak.jpa.entity.Company;

/**
 * describe here
 *
 * @author shiyulong
 * @since 2023/10/31
 */
public class CompanyRepresentation {

    private String id;
    private String name;

    public CompanyRepresentation() {
    }

    public CompanyRepresentation(Company company) {
        id = company.getId();
        name = company.getName();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}