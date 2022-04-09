package br.com.challenge.pix.itau.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoRegistersReturnedException extends RuntimeException{

    private String exceptionMessage;

    public NoRegistersReturnedException(String message){
        super();
        this.exceptionMessage = message;
    }
}
