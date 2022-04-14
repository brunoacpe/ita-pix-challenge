package br.com.challenge.pix.itau.services;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import br.com.challenge.pix.itau.dto.PixRegisterRequest;
import br.com.challenge.pix.itau.entity.PixRegister;
import br.com.challenge.pix.itau.exceptions.InvalidInputsException;
import br.com.challenge.pix.itau.repository.PixRegisterRepository;
import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ValidationServiceImpl implements ValidationService {

    public ValidationServiceImpl(PixRegisterRepository repository){
        this.registerRepository = repository;
    }
    private final PixRegisterRepository registerRepository;

    @Value("${regex.email}")
    private String REGEX_EMAIL;

    private final String CPF = "cpf";

    private final String CNPJ = "cnpj";

    private final String EMAIL = "email";

    @Override
    public void validateUserFirstName(String userFirstName) {
        if(Objects.isNull(userFirstName)||userFirstName.isEmpty())
            throw new InvalidInputsException("O primeiro nome do usuário DEVE ser preenchido.");

        if(userFirstName.length()>30)
            throw new InvalidInputsException("O primeiro nome do usuário não pode passar de 30 caracteres.");
    }

    @Override
    public void validateUserLastName(String userLastName) {
            if(userLastName.length()>45)
                throw new InvalidInputsException("O sobrenome do usuário não pode passar dos 45 caracteres.");
    }


    @Override
    public void validateRequest(PixRegisterRequest request) {
        String requestKeyType = request.getKeyType();
        String requestKeyValue = request.getKeyValue();
        String accountType = request.getAccountType();
        Integer accountNumber = request.getAccountNumber();
        Integer agencyNumber = request.getAgencyNumber();
        String userFirstName = request.getUserFirstName();
        String userLastName = request.getUserLastName();
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
        validateUserLastName(userLastName);
        validateUserFirstName(userFirstName);
    }


    @Override
    public void validatePatchRequest(PixRegisterRequest request) {
        String accountType = request.getAccountType();
        Integer accountNumber = request.getAccountNumber();
        Integer agencyNumber = request.getAgencyNumber();
        String userFirstName = request.getUserFirstName();
        String userLastName = request.getUserLastName();
        accountTypeValid(accountType);
        agencyNumberValid(agencyNumber);
        accountNumberValid(accountNumber);
        validateUserLastName(userLastName);
        validateUserFirstName(userFirstName);
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
