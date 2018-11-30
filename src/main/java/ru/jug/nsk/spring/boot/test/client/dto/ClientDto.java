package ru.jug.nsk.spring.boot.test.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDto {
    private UUID uuid;
    private String login;
    private String email;
    private String firstName;
    private String familyName;
    private boolean deleted;
}
