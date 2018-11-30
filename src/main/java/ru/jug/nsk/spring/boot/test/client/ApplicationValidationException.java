package ru.jug.nsk.spring.boot.test.client;

import lombok.Getter;

@Getter
public class ApplicationValidationException extends RuntimeException {

    private static final long serialVersionUID = 2325785326401275611L;
    private final String errorCode;
    private final Object[] arguments;

    public ApplicationValidationException(String errorCode, Object... arguments) {
        super(errorCode);
        this.errorCode = errorCode;
        this.arguments = arguments;
    }
}
