package ru.jug.nsk.spring.boot.test.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import ru.jug.nsk.spring.boot.test.client.dto.ClientDto;

import java.io.InputStream;
import java.util.List;

@Slf4j
public final class TestUtils {

    static final ParameterizedTypeReference<List<ClientDto>> LIST_DTO_TYPE = new ParameterizedTypeReference<List<ClientDto>>() {
    };

    public static <T> T loadResource(String resourceName, Class<T> type, ObjectMapper objectMapper, Class<?> resourceClass) {
        try (InputStream is = resourceClass.getResourceAsStream(resourceName)) {
            return objectMapper.readValue(is, type);
        } catch (Exception e) {
            TestResourceException ex = new TestResourceException(resourceName, type, e);
            log.error("load: " + ex.getMessage(), e);
            throw ex;
        }
    }

    @Getter
    private static class TestResourceException extends RuntimeException {

        private static final long serialVersionUID = 6981741484658439146L;
        private final String resourceName;
        private final Class<?> type;

        public <T> TestResourceException(String resourceName, Class<T> type, Exception cause) {
            super("Failed to load \"" + resourceName + "\" with \"" + type.getCanonicalName() + '"', cause);
            this.resourceName = resourceName;
            this.type = type;
        }
    }

    private TestUtils() {
        throw new AssertionError("Not for instantiation.");
    }
}
