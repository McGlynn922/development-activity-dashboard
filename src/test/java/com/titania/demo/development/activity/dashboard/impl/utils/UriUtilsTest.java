package com.titania.demo.development.activity.dashboard.impl.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UriUtilsTest {

    private static final String SINCE = "since";
    private static final String UNTIL = "until";

    @Test
    void buildQueryParameters_shouldReturnCorrectQueryParameters_whenValidValues() throws URISyntaxException {
        // given
        final String path = "https://my-github-repository.com";
        final Map<String, String> queryParameters = new LinkedHashMap<>();
        queryParameters.put(SINCE, "2023-01-01");
        queryParameters.put(UNTIL, "2024-12-31");

        // when
        final String actual = UriUtils.buildQueryParameters(path, queryParameters);

        // then
        assertEquals("https://my-github-repository.com?since=2023-01-01&until=2024-12-31", actual);
    }

    @Test
    void buildQueryParameters_shouldRemoveQueryParameters_whenNullValue() throws URISyntaxException {
        // given
        final String path = "https://my-github-repository.com";
        final Map<String, String> queryParameters = new LinkedHashMap<>();
        queryParameters.put(SINCE, "2023-01-01");
        queryParameters.put(UNTIL, null);

        // when
        final String actual = UriUtils.buildQueryParameters(path, queryParameters);

        // then
        assertEquals("https://my-github-repository.com?since=2023-01-01", actual);
    }

    @Test
    void buildQueryParameters_shouldThrowURISyntaxException_whenInvalidUrlPath() {
        // given
        final String path = "!@£$%^&*()";
        final Map<String, String> queryParameters = new LinkedHashMap<>();
        queryParameters.put(SINCE, "2023-01-01");
        queryParameters.put(UNTIL, "2024-12-31");

        // when
        // then
        assertThrows(URISyntaxException.class, () -> UriUtils.buildQueryParameters(path, queryParameters));
    }

    @ParameterizedTest
    @MethodSource("queryParameterValues")
    void buildQueryParameters_shouldThrowIllegalArgumentException_whenNullValues(final String path, final Map<String, String> queryParameters) {
        // given
        // when
        // then
        assertThrows(IllegalArgumentException.class, () -> UriUtils.buildQueryParameters(path, queryParameters));
    }

    private static List<Arguments> queryParameterValues() {
        return of(
                Arguments.of(null, Map.of(SINCE, "2023-01-01", UNTIL, "2024-12-31")),
                Arguments.of("https://my-github-repository.comz", null));
    }

}