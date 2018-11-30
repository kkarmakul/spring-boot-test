package ru.jug.nsk.spring.boot.test.client.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class CreateClientDto {
    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9_-]{3,32}")
    private String login;
    private String passwordHash;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String firstName;
    @NotNull
    private String familyName;
}
