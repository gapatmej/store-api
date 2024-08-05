package com.aperalta.store.web.rest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class HeaderUtil {

    private static final Logger log = LoggerFactory.getLogger(HeaderUtil.class);

    private static final String APPLICATION_NAME = "store-API";
    private HeaderUtil() {
    }

    public static HttpHeaders createAlert( String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-" + APPLICATION_NAME + "-alert", message);
        headers.add("X-" + APPLICATION_NAME + "-params", URLEncoder.encode(param, StandardCharsets.UTF_8));
        return headers;
    }

    public static HttpHeaders createEntityCreationAlert( String entityName, String param) {
        String message =  entityName + " is created with identifier " + param;
        return createAlert(message, param);
    }

    public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
        String message = entityName + " is updated with identifier " + param;
        return createAlert(message, param);
    }

    public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
        String message = entityName + " is deleted with identifier " + param;
        return createAlert(message, param);
    }

    public static HttpHeaders createFailureAlert(String entityName, String defaultMessage) {
        log.error("Entity processing failed, {}", defaultMessage);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-" + APPLICATION_NAME + "-error", defaultMessage);
        headers.add("X-" + APPLICATION_NAME + "-params", entityName);
        return headers;
    }


}
