package es.upm.miw.devops.resources.exceptionshandler;

import es.upm.miw.devops.services.exceptions.ConflictException;
import es.upm.miw.devops.services.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApiExceptionHandlerTest {

    private ApiExceptionHandler apiExceptionHandler;

    @BeforeEach
    void setUp() {
        this.apiExceptionHandler = new ApiExceptionHandler();
    }

    @Test
    void testNotFoundRequest() {
        NotFoundException exception = new NotFoundException("Not found");
        ErrorMessage errorMessage = this.apiExceptionHandler.notFoundRequest(exception);

        assertEquals("NotFoundException", errorMessage.getError());
        assertEquals("Not found", errorMessage.getMessage());
    }

    @Test
    void testConflictRequest() {
        ConflictException exception = new ConflictException("Conflict");
        ErrorMessage errorMessage = this.apiExceptionHandler.conflictRequest(exception);

        assertEquals("ConflictException", errorMessage.getError());
        assertEquals("Conflict", errorMessage.getMessage());
    }

    @Test
    void testFatalErrorUnexpected() {
        Exception exception = new Exception("Unexpected");
        ErrorMessage errorMessage = this.apiExceptionHandler.fatalErrorUnexpected(exception);

        assertEquals("Exception", errorMessage.getError());
        assertEquals("Unexpected", errorMessage.getMessage());
    }
}