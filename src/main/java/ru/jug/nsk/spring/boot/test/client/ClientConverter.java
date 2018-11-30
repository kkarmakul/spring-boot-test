package ru.jug.nsk.spring.boot.test.client;

import org.mapstruct.Mapper;
import ru.jug.nsk.spring.boot.test.client.dto.ClientDto;
import ru.jug.nsk.spring.boot.test.client.dto.CreateClientDto;
import ru.jug.nsk.spring.boot.test.client.dto.ModifyClientDto;
import ru.jug.nsk.spring.boot.test.client.entity.Client;

@SuppressWarnings("AbstractClassNeverImplemented")
@Mapper
public abstract class ClientConverter {

    public abstract Client fromDto(CreateClientDto dto);
    public abstract ClientDto toDto(Client client);
    public abstract Iterable<ClientDto> toDto(Iterable<Client> client);

    public void merge(Client client, ModifyClientDto dto) {
        client.setEmail(dto.getEmail());
        client.setFamilyName(dto.getFamilyName());
        client.setFirstName(dto.getFirstName());
        client.setDeleted(dto.isDeleted());
    }
}
