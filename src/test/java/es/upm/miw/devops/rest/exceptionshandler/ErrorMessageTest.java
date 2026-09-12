package es.upm.miw.devops.rest.exceptionshandler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorMessageTest {

    @Test
    void testErrorMessageConstructorAndGetters() {
        Exception exception = new IllegalArgumentException("Invalid argument provided");
        Integer code = 400;

        ErrorMessage errorMessage = new ErrorMessage(exception, code);

        assertEquals("IllegalArgumentException", errorMessage.getError());
        assertEquals("Invalid argument provided", errorMessage.getMessage());
        assertEquals(400, errorMessage.getCode());
    }

    @Test
    void testErrorMessageToString() {
        Exception exception = new RuntimeException("Unexpected error");
        Integer code = 500;

        ErrorMessage errorMessage = new ErrorMessage(exception, code);
        String expectedToString = "ErrorMessage{" +
                "error='RuntimeException'" +
                ", message='Unexpected error'" +
                ", code=500" +
                '}';

        assertEquals(expectedToString, errorMessage.toString());
    }
}