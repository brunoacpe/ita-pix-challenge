package br.com.challenge.pix.itau.exceptions;

import lombok.Getter;

@Getter
public class InvalidInputsException extends RuntimeException{

    private  String exceptionMessage;

    public InvalidInputsException(String exceptionMessage){
        super();
        this.exceptionMessage = exceptionMessage;
    }

}
