package ru.jug.nsk.spring.boot.test.client.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateClientDto {
    private String login;
    private String passwordHash;
    private String email;
    private String firstName;
    private String familyName;
}
