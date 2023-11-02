package com.dadaer.keycloak.example.spi;

import org.keycloak.provider.Provider;
import org.keycloak.provider.ProviderFactory;
import org.keycloak.provider.Spi;

/**
 * describe here
 *
 * @author shiyulong
 * @since 2023/10/31
 */
public class ExampleSpi implements Spi {
    @Override
    public boolean isInternal() {
        return false;
    }

    @Override
    public String getName() {
        return "example";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return ExampleServiceProvider.class;
    }

    @Override
    public Class<? extends ProviderFactory<ExampleServiceProvider>> getProviderFactoryClass() {
        return ExampleServiceProviderFactory.class;
    }
}
