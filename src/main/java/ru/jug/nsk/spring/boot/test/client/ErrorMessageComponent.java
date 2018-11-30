package ru.jug.nsk.spring.boot.test.client;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;

import java.util.Locale;
import java.util.Objects;

@Component
@AllArgsConstructor
@Slf4j
public class ErrorMessageComponent {

    private final MessageSource messageSource;

    public String getDescription(ObjectError error, Locale locale) {
        Objects.requireNonNull(error);
        try {
            return messageSource.getMessage(error, locale);
        } catch (NoSuchMessageException ex) {
            log.error("Failed to find a message.", ex);
            return error.getCode();
        }
    }

    public String getDescription(ApplicationValidationException ex, Locale locale) {
        Objects.requireNonNull(ex);
        try {
            return messageSource.getMessage(ex.getErrorCode(), ex.getArguments(), locale);
        } catch (NoSuchMessageException ex2) {
            log.error("Failed to find a message.", ex2);
            return ex.getErrorCode();
        }
    }
}
