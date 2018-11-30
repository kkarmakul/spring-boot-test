package ru.jug.nsk.spring.boot.test.client;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import ru.jug.nsk.spring.boot.test.client.dto.ClientDto;
import ru.jug.nsk.spring.boot.test.client.dto.CreateClientDto;
import ru.jug.nsk.spring.boot.test.client.dto.ModifyClientDto;

import java.util.List;
import java.util.UUID;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@Slf4j
public class ClientTests extends BaseApplicationTest {

    @Test
    public void get() {
        ClientDto actual = perform(null, HttpMethod.GET,
                "clients/36dc24c7-7cd1-4c73-b6cd-ab460b4c7636", ClientDto.class);

        ClientDto expected = loadResource("client.get.json", ClientDto.class);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void create() {
        CreateClientDto dto = loadResource("client.create.json", CreateClientDto.class);

        UUID uuid = perform(dto, HttpMethod.POST, "clients/create", UUID.class, HttpStatus.CREATED);

        ClientDto actual = getClient(uuid);
        ClientDto expected = loadResource("client.created.json", ClientDto.class);
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "uuid");
        assertThat(actual.getUuid()).isNotNull(); // TODO: sublists, dates, floats, etc.
    }

    @Test
    public void modify() {
        ModifyClientDto dto = loadResource("client.modify.json", ModifyClientDto.class);

        perform(dto, HttpMethod.PUT, "clients/modify", Void.class, HttpStatus.NO_CONTENT);

        ClientDto expected = loadResource("client.modified.json", ClientDto.class);
        assertThat(getClient(expected.getUuid())).isEqualTo(expected);
    }

    @Test
    public void delete() {
        UUID uuid = UUID.fromString("8848c59b-a40d-49be-ae4a-01e9762f5dcf");
        perform(uuid, HttpMethod.DELETE,
                "clients/delete", Void.class, HttpStatus.NO_CONTENT);

        assertThat(getClient(uuid))
                .isEqualTo(loadResource("client.deleted.json", ClientDto.class));
    }

    @Test
    public void search() {
        List<ClientDto> actual = perform(null, HttpMethod.GET,
                "clients/search?login=login_get", TestUtils.LIST_DTO_TYPE, HttpStatus.OK);

        assertThat(actual).isEqualTo(singletonList(
                loadResource("client.get.json", ClientDto.class)));
    }

    private ClientDto getClient(UUID uuid) {
        return performGet("clients/" + uuid, ClientDto.class);
    }
}