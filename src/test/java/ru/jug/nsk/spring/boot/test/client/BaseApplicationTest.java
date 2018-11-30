package ru.jug.nsk.spring.boot.test.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseApplicationTest {

    @LocalServerPort
    private int serverPort;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    protected <T> T loadResource(String resourceName, Class<T> _class) {
        return TestUtils.loadResource(resourceName, _class, objectMapper, getClass());
    }

    protected <T> T performGet(String pathEnd, Class<T> dtoClass) {
        return perform(null, HttpMethod.GET, pathEnd, dtoClass);
    }

    protected <T> T perform(Object dto, HttpMethod method, String pathEnd, Class<T> responseClass) {
        return perform(dto, method, pathEnd, responseClass, HttpStatus.OK);
    }

    protected <T> T perform(Object dto, HttpMethod method, String pathEnd, Class<T> responseClass, HttpStatus status) {
        return perform(dto, method, pathEnd, ParameterizedTypeReference.forType(responseClass), status);
    }
    @SneakyThrows
    protected <T> T perform(Object dto, HttpMethod method, String pathEnd, ParameterizedTypeReference<T> responseClass, HttpStatus status) {
        URI uri = new URI(String.format("http://localhost:%d/api/v1/%s", serverPort, pathEnd));
        ResponseEntity<T> responseEntity = testRestTemplate.exchange(
                new RequestEntity<>(dto, method, uri), responseClass);

        assertThat(responseEntity.getStatusCode()).isEqualTo(status);
        if (status != HttpStatus.NO_CONTENT) {
            assertThat(responseEntity.getBody()).isNotNull();
        }
        return responseEntity.getBody();
    }
}
