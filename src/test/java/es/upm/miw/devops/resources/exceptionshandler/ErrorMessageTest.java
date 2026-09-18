package es.upm.miw.devops.resources.exceptionshandler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorMessageTest {

    @Test
    void testErrorMessageConstruction() {
        Exception exception = new RuntimeException("Error message test");
        ErrorMessage errorMessage = new ErrorMessage(exception, "400");

        assertEquals("RuntimeException", errorMessage.getError());
        assertEquals("Error message test", errorMessage.getMessage());
        assertEquals("400", errorMessage.getCode());
    }
}