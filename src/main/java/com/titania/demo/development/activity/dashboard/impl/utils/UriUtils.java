package com.titania.demo.development.activity.dashboard.impl.utils;

import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;
import java.util.Map;

public final class UriUtils {

    private UriUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String buildQueryParameters(final String urlPath, final Map<String, String> parameters) throws URISyntaxException {
        if (urlPath == null || parameters == null) {
            throw new IllegalArgumentException("URL and parameters must not be null");
        }

        final URIBuilder uriBuilder = new URIBuilder(urlPath);
        parameters.entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .forEach(entry -> uriBuilder.addParameter(entry.getKey(), entry.getValue()));

        return uriBuilder.toString();
    }
}