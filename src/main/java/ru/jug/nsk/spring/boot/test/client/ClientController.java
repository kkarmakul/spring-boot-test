package ru.jug.nsk.spring.boot.test.client;

import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.jug.nsk.spring.boot.test.client.dto.ClientDto;
import ru.jug.nsk.spring.boot.test.client.dto.CreateClientDto;
import ru.jug.nsk.spring.boot.test.client.dto.ModifyClientDto;
import ru.jug.nsk.spring.boot.test.client.entity.Client;

import javax.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/clients")
@AllArgsConstructor
@Slf4j
public class ClientController {

    private final ClientConverter converter;
    private final ClientService clientService;

    @GetMapping("{uuid}")
    public ClientDto get(@PathVariable UUID uuid) {
        return clientService.getOne(uuid, converter::toDto);
    }

    @GetMapping("search")
    public Iterable<ClientDto> search(@QuerydslPredicate(root = Client.class) Predicate predicate) {
        return clientService.search(predicate, converter::toDto);
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public UUID create(@RequestBody @Valid CreateClientDto dto) {
        log.debug("create: Client is being created: {}", dto);
        UUID uuid = clientService.create(converter.fromDto(dto));
        log.info("create: Client was saved under UUID: {}", uuid);
        return uuid;
    }

    @PutMapping("modify")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modify(@RequestBody ModifyClientDto dto) {
        log.debug("modify: Client is being modified: {}", dto);
        clientService.modify(dto);
        log.info("modify: Client was modified: {}", dto);
    }

    @DeleteMapping("delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody UUID uuid) {
        log.debug("delete: Client is being deleted, UUID: {}", uuid);
        clientService.delete(uuid);
        log.info("delete: Client was marked as deleted, UUID: {}", uuid);
    }
}
