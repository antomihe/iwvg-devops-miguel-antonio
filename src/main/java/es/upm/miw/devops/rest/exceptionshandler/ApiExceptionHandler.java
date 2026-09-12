package es.upm.miw.devops.rest.exceptionshandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleResponseStatusException(ResponseStatusException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception, exception.getStatusCode().value());
        return new ResponseEntity<>(errorMessage, exception.getStatusCode());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseBody
    public ErrorMessage noResourceFoundRequest(NoResourceFoundException exception) {
        return new ErrorMessage(
                new RuntimeException("Route not found. Try: /actuator/info or /swagger-ui.html"),
                HttpStatus.NOT_FOUND.value()
        );
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorMessage exception(Exception exception) {
        return new ErrorMessage(exception, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}