package br.com.challenge.pix.itau.utils;

import org.springframework.stereotype.Service;


public interface ValidationService {

    void keyTypeMatcher(String keyValue);

    String cpfValidations(String cpf);

    boolean validCpfCalculations(String cpf);

    boolean validCnpjCalculations(String cnpj);

    String cnpjValidations(String cnpj);

    String emailValidations(String email);

    boolean accountTypeValid(String accountType);
}
