package ru.jug.nsk.spring.boot.test.client;

import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jug.nsk.spring.boot.test.client.dto.ModifyClientDto;
import ru.jug.nsk.spring.boot.test.client.entity.Client;

import java.util.UUID;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private ClientConverter converter;

    public UUID create(Client client) {
        clientRepository.save(client);
        return client.getUuid();
    }

    @Transactional
    public void modify(ModifyClientDto dto) {
        Client client = clientRepository.getOne(dto.getUuid());
        converter.merge(client, dto);
        clientRepository.save(client);
    }

    @Transactional
    public void delete(UUID uuid) {
        Client client = clientRepository.getOne(uuid);
        client.setDeleted(true);
        clientRepository.save(client);
    }

    @Transactional
    public <T> T getOne(UUID clientUUID, Function<Client, T> c) {
        return c.apply(clientRepository.getOne(clientUUID));
    }

    public <T> Iterable<T> search(Predicate predicate, Function<Iterable<Client>, Iterable<T>> c) {
        return c.apply(clientRepository.findAll(predicate));

    }
}
