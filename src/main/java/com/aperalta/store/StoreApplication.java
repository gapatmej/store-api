package com.aperalta.store;

import com.aperalta.store.config.ApplicationProperties;
import com.aperalta.store.repository.enumeration.QueryOperationEnum;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.stream.Collectors;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableConfigurationProperties({ApplicationProperties.class})
public class StoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreApplication.class, args);
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer() {
        String charsPermited = Arrays.stream(QueryOperationEnum.values())
                .map(QueryOperationEnum::value)
                .collect(Collectors.joining());
        return (factory) -> factory.addConnectorCustomizers((connector) -> {
            connector.setProperty("relaxedQueryChars", charsPermited);
            connector.setProperty("relaxedPathChars", charsPermited);
        });
    }

}
