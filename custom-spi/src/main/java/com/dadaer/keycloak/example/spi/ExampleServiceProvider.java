package com.dadaer.keycloak.example.spi;

import org.keycloak.provider.Provider;

/**
 * describe here
 *
 * @author shiyulong
 * @since 2023/10/31
 */
public interface ExampleServiceProvider extends Provider {

    String sayHi(String name);

}
