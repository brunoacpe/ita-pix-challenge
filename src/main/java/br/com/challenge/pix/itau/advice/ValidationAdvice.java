package br.com.challenge.pix.itau.advice;

import br.com.challenge.pix.itau.exceptions.NoRegistersReturnedException;
import br.com.challenge.pix.itau.exceptions.InvalidInputsException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ValidationAdvice {
    @ExceptionHandler(InvalidInputsException.class)
    public ResponseEntity<Object> handleInvalidKeyTypeException(InvalidInputsException e){
        log.error("Ocorreu um erro durante a validação do registro da chave PIX. Mensagem: {}", e.getExceptionMessage());
        Map<String,Object> response = new LinkedHashMap<>();
        response.put("exception_message", e.getExceptionMessage());
        response.put("timestamp", new Date());
        return new ResponseEntity<>(
                response,
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }

    @ExceptionHandler(NoRegistersReturnedException.class)
    public ResponseEntity<Object> handleInvalidIdException(NoRegistersReturnedException e){
        log.error("Ocorreu um erro durante a busca de um registro. Mensagem: {}", e.getExceptionMessage());
        Map<String,Object> response = new LinkedHashMap<>();
        response.put("exception_message", e.getExceptionMessage());
        response.put("timestamp", new Date());
        return new ResponseEntity<>(
                response,
                HttpStatus.NOT_FOUND
        );
    }



}
