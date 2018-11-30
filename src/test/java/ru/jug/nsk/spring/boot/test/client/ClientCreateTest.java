package ru.jug.nsk.spring.boot.test.client;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ru.jug.nsk.spring.boot.test.client.dto.ClientDto;
import ru.jug.nsk.spring.boot.test.client.dto.CreateClientDto;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class ClientCreateTest {

    @LocalServerPort
    private int serverPort;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientConverter converter;

    @Test
    public void create() {
        CreateClientDto dto = new CreateClientDto();
        dto.setLogin("bsmith");
        dto.setFamilyName("Bob");
        dto.setFirstName("Smith"); // TODO load from resource

        ResponseEntity<UUID> re = testRestTemplate.postForEntity(
                String.format("http://localhost:%d/api/v1/clients/create", serverPort),
                dto,
                UUID.class);
        assertThat(re.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(re.getBody()).isNotNull(); // TODO move to Utils

        ClientDto result = clientService.getOne(re.getBody(), converter::toDto); // TODO через GET-запрос
        assertThat(result.getFamilyName()).isEqualTo(dto.getFamilyName());
        assertThat(result.getFirstName()).isEqualTo(dto.getFirstName());
        // TODO load expected from resource
        // TODO compare with expected
    }
}