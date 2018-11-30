package ru.jug.nsk.spring.boot.test.client;

import com.google.common.collect.ImmutableList;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import ru.jug.nsk.spring.boot.test.client.dto.CreateClientDto;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
@Slf4j
public class ValidateClientCreationTests extends AbstractParameterizedApplicationTest {

    static final ParameterizedTypeReference<List<String>> LIST_OF_STRING_TYPE = new ParameterizedTypeReference<List<String>>() {
    };

    @Parameterized.Parameter
    public ValidationTestArguments args;

    @Parameterized.Parameters
    public static Iterable<ValidationTestArguments> init() {
        return ImmutableList.of(
                args(dto -> dto.setLogin(null),
                        errors("Логин является обязательным полем")),
                args(dto -> dto.setLogin(" \t\r\n "),
                        errors("Логин должен состоять из 3 и более латинский букв и цифр")),
                args(dto -> dto.setLogin("логин"),
                        errors("Логин должен состоять из 3 и более латинский букв и цифр")),
                args(dto -> dto.setLogin("login_get"),
                        errors("Логин login_get уже используется")),

                args(dto -> dto.setEmail(null),
                        errors("Электронная почта является обязательным полем")),
                args(dto -> dto.setEmail("email"),
                        errors("Некорректный адрес электронной почты")),

                args(dto -> dto.setFirstName(null),
                        errors("Имя является обязательным полем")),
                args(dto -> dto.setFamilyName(null),
                        errors("Фамилия является обязательным полем")));
    }

    @Test
    public void validate() {
        CreateClientDto dto = loadResource("client.create.json", CreateClientDto.class);
        args.modifier.accept(dto);
        List<String> actual = perform(dto, HttpMethod.POST,
                "clients/create", LIST_OF_STRING_TYPE, HttpStatus.BAD_REQUEST);

        assertThat(actual).containsExactlyInAnyOrderElementsOf(args.getExpectedErrors());
    }

    private static ValidationTestArguments args(Consumer<CreateClientDto> modifier, Iterable<String> expectedErrors) {
        return new ValidationTestArguments(modifier, expectedErrors);
    }

    private static Iterable<String> errors(String... errors) {
        return Arrays.stream(errors).collect(Collectors.toList());
    }

    @Value
    private static class ValidationTestArguments {

        private final Consumer<CreateClientDto> modifier;
        private final Iterable<String> expectedErrors;
    }
}