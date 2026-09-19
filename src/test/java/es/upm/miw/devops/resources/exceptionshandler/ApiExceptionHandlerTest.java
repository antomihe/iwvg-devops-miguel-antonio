package es.upm.miw.devops.resources.exceptionshandler;

import es.upm.miw.devops.services.exceptions.ConflictException;
import es.upm.miw.devops.services.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ApiExceptionHandlerTest {

    private ApiExceptionHandler apiExceptionHandler;

    @BeforeEach
    void setUp() {
        this.apiExceptionHandler = new ApiExceptionHandler();
    }

    @Test
    void testNotFoundRequest() {
        NotFoundException exception = new NotFoundException("Not found test");
        ErrorMessage message = this.apiExceptionHandler.notFoundRequest(exception);

        assertThat(message.getError()).isEqualTo("NotFoundException");
        assertThat(message.getMessage()).isEqualTo("Not found test");
        assertThat(message.getCode()).contains("404");
    }

    @Test
    void testConflictRequest() {
        ConflictException exception = new ConflictException("Conflict test");
        ErrorMessage message = this.apiExceptionHandler.conflictRequest(exception);

        assertThat(message.getError()).isEqualTo("ConflictException");
        assertThat(message.getMessage()).isEqualTo("Conflict test");
        assertThat(message.getCode()).contains("409");
    }

    @Test
    void testFatalErrorUnexpected() {
        RuntimeException exception = new RuntimeException("Unexpected test error");
        ErrorMessage message = this.apiExceptionHandler.fatalErrorUnexpected(exception);

        assertThat(message.getError()).isEqualTo("RuntimeException");
        assertThat(message.getMessage()).isEqualTo("Unexpected test error");
        assertThat(message.getCode()).contains("500");
    }
}