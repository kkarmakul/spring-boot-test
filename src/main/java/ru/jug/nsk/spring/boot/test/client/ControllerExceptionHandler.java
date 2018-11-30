package ru.jug.nsk.spring.boot.test.client;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;

import java.util.Collections;

import static java.util.stream.Collectors.toList;

@Slf4j
@RestControllerAdvice
@AllArgsConstructor
public class ControllerExceptionHandler {

    private final ErrorMessageComponent errorMessageService;
    private final LocaleResolver localeResolver;

    @Autowired
    protected HttpServletRequest httpRequest;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Iterable<String> handleArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("handleArgumentNotValidException: \"{}\"", ex.getMessage());
        return ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> errorMessageService.getDescription(error, localeResolver.resolveLocale(httpRequest)))
                .collect(toList());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ApplicationValidationException.class)
    public Iterable<String> handleApplicationValidationException(ApplicationValidationException ex) {
        log.error("handleApplicationValidationException: \"{}\"", ex.getMessage());
        return Collections.singletonList(errorMessageService.getDescription(ex, localeResolver.resolveLocale(httpRequest)));
    }
}
