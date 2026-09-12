package es.upm.miw.devops.rest.exceptionshandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ApiExceptionHandlerTest {

    private ApiExceptionHandler apiExceptionHandler;

    @BeforeEach
    void setUp() {
        apiExceptionHandler = new ApiExceptionHandler();
    }

    @Test
    void testHandleResponseStatusException() {
        ResponseStatusException exception = new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource missing");

        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleResponseStatusException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().getCode());
        assertEquals("ResponseStatusException", response.getBody().getError());
    }

    @Test
    void testNoResourceFoundRequest() {
        NoResourceFoundException exception = new NoResourceFoundException(HttpMethod.GET, "/unknown-endpoint");

        ErrorMessage errorMessage = apiExceptionHandler.noResourceFoundRequest(exception);

        assertNotNull(errorMessage);
        assertEquals(404, errorMessage.getCode());
        assertEquals("RuntimeException", errorMessage.getError());
        assertEquals("Route not found. Try: /actuator/info or /swagger-ui.html", errorMessage.getMessage());
    }

    @Test
    void testHandleHttpMessageNotReadableException() {
        ApiExceptionHandler apiExceptionHandler = new ApiExceptionHandler();
        HttpMessageNotReadableException exception = new HttpMessageNotReadableException("Body missing");

        ErrorMessage errorMessage = apiExceptionHandler.handleHttpMessageNotReadableException(exception);

        assertNotNull(errorMessage);
        assertEquals(400, errorMessage.getCode());
        assertEquals("HttpMessageNotReadableException", errorMessage.getError());
    }

    @Test
    void testGenericException() {
        Exception exception = new NullPointerException("Null reference encountered");

        ErrorMessage errorMessage = apiExceptionHandler.exception(exception);

        assertNotNull(errorMessage);
        assertEquals(500, errorMessage.getCode());
        assertEquals("NullPointerException", errorMessage.getError());
        assertEquals("Null reference encountered", errorMessage.getMessage());
    }
}