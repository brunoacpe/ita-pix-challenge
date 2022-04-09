package br.com.challenge.pix.itau.services;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import br.com.challenge.pix.itau.dto.PixRegisterRequest;
import br.com.challenge.pix.itau.exceptions.InvalidInputsException;
import br.com.challenge.pix.itau.repository.PixRegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ValidationServiceImpl implements ValidationService {
    @Autowired
    private PixRegisterRepository registerRepository;

    @Value("${regex.email}")
    private String REGEX_EMAIL;

    private final String CPF = "cpf";

    private final String CNPJ = "cnpj";

    private final String EMAIL = "email";

    @Override
    public void validateRequest(PixRegisterRequest request) {
        String requestKeyType = request.getKeyType();
        String requestKeyValue = request.getKeyValue();
        String accountType = request.getAccountType();
        Integer accountNumber = request.getAccountNumber();
        Integer agencyNumber = request.getAgencyNumber();
        keyTypeValidation(requestKeyType);
        if (requestKeyType.equalsIgnoreCase(CPF)) {
            cpfValidations(requestKeyValue);
        } else if (requestKeyType.equalsIgnoreCase(CNPJ)) {
            cnpjValidations(requestKeyValue);
        } else {
            emailValidations(requestKeyValue);
        }
        accountTypeValid(accountType);
        agencyNumberValid(agencyNumber);
        accountNumberValid(accountNumber);
    }

    @Override
    public void keyTypeValidation(String keyType) {
        if (!keyType.equalsIgnoreCase(CPF) && !keyType.equalsIgnoreCase(CNPJ) && !keyType.equalsIgnoreCase(EMAIL))
            throw new InvalidInputsException("O valor da chave pix é inválido. Precisa ser um cpf, cnpj ou um email.");
    }


    @Override
    public void cpfValidations(String cpf) {
        if(registerRepository.findByCPF(cpf).isPresent())
            throw new InvalidInputsException("CPF já registrado como chave pix.");

        try {
            CPFValidator validator = new CPFValidator();
            validator.assertValid(cpf);
        } catch (InvalidStateException e) {
            throw new InvalidInputsException("CPF inválido.");
        }

    }

    @Override
    public void cnpjValidations(String cnpj) {
        if (registerRepository.findByCNPJ(cnpj).isPresent())
            throw new InvalidInputsException("Já existe um cadastro desse CNPJ como chave pix.");

        try{
            CNPJValidator validator = new CNPJValidator();
            validator.assertValid(cnpj);
        } catch (InvalidStateException e){
            throw new InvalidInputsException("CNPJ inválido.");
        }

    }

    @Override
    public boolean validCnpjCalculations(String CNPJ) {

        if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111") ||
                CNPJ.equals("22222222222222") || CNPJ.equals("33333333333333") ||
                CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555") ||
                CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777") ||
                CNPJ.equals("88888888888888") || CNPJ.equals("99999999999999") ||
                (CNPJ.length() != 14))
            return (false);

        char dig13, dig14;
        int sm, i, r, num, peso;


        sm = 0;
        peso = 2;
        for (i = 11; i >= 0; i--) {

            num = (int) (CNPJ.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso + 1;
            if (peso == 10)
                peso = 2;
        }

        r = sm % 11;
        if ((r == 0) || (r == 1))
            dig13 = '0';
        else dig13 = (char) ((11 - r) + 48);


        sm = 0;
        peso = 2;
        for (i = 12; i >= 0; i--) {
            num = (int) (CNPJ.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso + 1;
            if (peso == 10)
                peso = 2;
        }

        r = sm % 11;
        if ((r == 0) || (r == 1))
            dig14 = '0';
        else dig14 = (char) ((11 - r) + 48);


        if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13)))
            return (true);
        else return (false);

    }


    @Override
    public void emailValidations(String email) {

        if (registerRepository.findByEmail(email).isPresent())
            throw new InvalidInputsException("Já existe esse email cadastrado como chave pix.");

        if (email.length() > 77)
            throw new InvalidInputsException("A quantidade de caracteres do e-mail deve ser inferior ou igual a 77.");

    }

    @Override
    public void accountTypeValid(String accountType) {
        if (Objects.isNull(accountType) || accountType.isBlank())
            throw new InvalidInputsException("O tipo da conta não pode ser vazio nem nulo.");
        if (!accountType.equalsIgnoreCase("corrente") && !accountType.equalsIgnoreCase("poupança"))
            throw new InvalidInputsException("O tipo da conta deve ser ou CORRENTE ou POUPANÇA.");
    }

    @Override
    public void accountNumberValid(Integer accountNumber) {
        if (Objects.isNull(accountNumber) || String.valueOf(accountNumber).isEmpty())
            throw new InvalidInputsException("O número da conta deve ser informado obrigatóriamente!");
        if (String.valueOf(accountNumber).length() > 8)
            throw new InvalidInputsException("O número da conta deve ter 8 ou menos dígitos!");
    }

    @Override
    public void agencyNumberValid(Integer agencyNumber) {
        if (Objects.isNull(agencyNumber) || String.valueOf(agencyNumber).isEmpty())
            throw new InvalidInputsException("O número da agência deve ser informado obrigatóriamente!");
        if (String.valueOf(agencyNumber).length() > 4)
            throw new InvalidInputsException("O número da agência deve ter 4 ou menos dígitos!");
    }
}
