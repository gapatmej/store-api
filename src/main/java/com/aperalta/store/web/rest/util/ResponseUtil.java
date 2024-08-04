package com.aperalta.store.web.rest.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface ResponseUtil {

    static <X> ResponseEntity wrapOrNotFound(Optional<X> maybeResponse) {
        return wrapOrNotFound(maybeResponse, null);
    }

    static <X> ResponseEntity wrapOrNotFound(Optional<X> maybeResponse, HttpHeaders header) {
        return maybeResponse.map((response) -> ResponseEntity.ok().headers(header).body(response)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
