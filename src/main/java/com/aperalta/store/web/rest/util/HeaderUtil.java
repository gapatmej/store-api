package com.aperalta.store.web.rest.util;

import org.springframework.http.HttpHeaders;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class HeaderUtil {

    private static final String APPLICATION_NAME = "store-API";
    private HeaderUtil() {
    }

    public static HttpHeaders createAlert( String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-" + APPLICATION_NAME + "-alert", message);
        headers.add("X-" + APPLICATION_NAME + "-params", URLEncoder.encode(param, StandardCharsets.UTF_8));
        return headers;
    }

    public static HttpHeaders createEntityCreationAlert(boolean enableTranslation, String entityName, String param) {
        String message = enableTranslation ? entityName + ".crudMessages.created"
            : "A new " + entityName + " is created with identifier " + param;
        return createAlert(message, param);
    }

}
