package es.upm.miw.devops.resources.exceptionshandler;

import es.upm.miw.devops.services.exceptions.ConflictException;
import es.upm.miw.devops.services.exceptions.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Log4j2
@ControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public ErrorMessage badRequest(Exception exception) {
        log.debug("MethodArgumentNotValidException handled: {}", exception.getMessage());
        return new ErrorMessage(exception, HttpStatus.BAD_REQUEST.toString());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class, NoResourceFoundException.class})
    @ResponseBody
    public ErrorMessage notFoundRequest(Exception exception) {
        log.debug("NotFound / NoResourceFound handled: {}", exception.getMessage());
        return new ErrorMessage(exception, HttpStatus.NOT_FOUND.toString());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({ConflictException.class})
    @ResponseBody
    public ErrorMessage conflictRequest(Exception exception) {
        log.debug("ConflictException handled: {}", exception.getMessage());
        return new ErrorMessage(exception, HttpStatus.CONFLICT.toString());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ErrorMessage fatalErrorUnexpected(Exception exception) {
        log.error("Unexpected error occurred", exception);
        return new ErrorMessage(exception, HttpStatus.INTERNAL_SERVER_ERROR.toString());
    }
}