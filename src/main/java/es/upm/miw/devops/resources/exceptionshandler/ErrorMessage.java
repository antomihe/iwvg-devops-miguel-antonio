package es.upm.miw.devops.resources.exceptionshandler;

import lombok.Getter;

@Getter
public class ErrorMessage {

    private final String error;
    private final String message;
    private final String code;

    public ErrorMessage(Exception exception, String code) {
        this.error = exception.getClass().getSimpleName();
        this.message = exception.getMessage();
        this.code = code;
    }
}