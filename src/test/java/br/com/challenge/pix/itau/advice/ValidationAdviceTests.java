package br.com.challenge.pix.itau.advice;

import br.com.challenge.pix.itau.exceptions.InvalidInputsException;
import br.com.challenge.pix.itau.exceptions.NoRegistersReturnedException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidationAdviceTests {

    private final ValidationAdvice underTest = new ValidationAdvice();



    @Test
    void shouldHandleInvalidKeyTypeException(){
        InvalidInputsException e = new InvalidInputsException("Inputs inválidos.");

        ResponseEntity<Object> response = underTest
                .handleInvalidKeyTypeException(e);

        assertNotNull(response);
        assertTrue(response.getBody().toString().contains("exception_message"));
    }

    @Test
    void shouldHandleNoRegistersReturnedException(){
        NoRegistersReturnedException e = new NoRegistersReturnedException("Nenhum registro foi retornado na requisição.");

        ResponseEntity<Object> response = underTest
                .handleNoRegistersReturnedException(e);

        assertNotNull(response);
        assertTrue(response.getBody().toString().contains("exception_message"));
    }
}
