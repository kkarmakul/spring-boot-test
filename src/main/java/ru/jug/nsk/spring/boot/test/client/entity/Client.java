package ru.jug.nsk.spring.boot.test.client.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.UUID;

@Entity
@Data
public class Client {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID uuid;
    private String login;
    private String passwordHash;
    private String email;
    private String firstName;
    private String familyName;
    private boolean deleted;
}
