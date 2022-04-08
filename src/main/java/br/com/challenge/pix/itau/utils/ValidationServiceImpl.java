package br.com.challenge.pix.itau.utils;

import br.com.challenge.pix.itau.exceptions.InvalidInputsException;
import br.com.challenge.pix.itau.repository.PixRegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.regex.Pattern;

@Service
public class ValidationServiceImpl implements ValidationService {
    @Autowired
    private PixRegisterRepository registerRepository;
    @Value("${regex.cpf}")
    private String REGEX_CPF;

    @Value("${regex.cnpj}")
    private String REGEX_CNPJ;

    @Value("${regex.email}")
    private String REGEX_EMAIL;

    @Override
    public void keyTypeMatcher(String keyValue) {
        Pattern cpfPattern = Pattern.compile(REGEX_CPF);

        Pattern cnpjPattern = Pattern.compile(REGEX_CNPJ);

        Pattern emailPattern = Pattern.compile(REGEX_EMAIL);

        if (cpfPattern.matcher(keyValue).matches()) {
            cpfValidations(keyValue);
            return;
        }

        if (cnpjPattern.matcher(keyValue).matches()) {
            cnpjValidations(keyValue);
            return;
        }

        if (emailPattern.matcher(keyValue).matches()) {
            emailValidations(keyValue);
            return;
        }


        throw new InvalidInputsException("O valor da chave pix é inválido. Precisa ser um cpf, cnpj ou um email.");
    }


    @Override
    public String cpfValidations(String cpf) {
        if (registerRepository.findByCPF(cpf).isPresent())
            throw new InvalidInputsException("Já existe um cadastro desse CPF como chave pix.");

        if (cpf.length() > 11)
            throw new InvalidInputsException("A quantidade de caracteres do CPF deve ser inferior ou igual a 11.");

        if (!validCpfCalculations(cpf))
            throw new InvalidInputsException("Esse CPF é inválido.");

        return null;
    }

    @Override
    public boolean validCpfCalculations(String strCpf) {
        int iDigito1Aux = 0, iDigito2Aux = 0, iDigitoCPF;
        int iDigito1 = 0, iDigito2 = 0, iRestoDivisao = 0;
        String strDigitoVerificador, strDigitoResultado;

        if (!strCpf.substring(0, 1).equals("")) {
            try {
                strCpf = strCpf.replace('.', ' ');
                strCpf = strCpf.replace('-', ' ');
                strCpf = strCpf.replaceAll(" ", "");
                for (int iCont = 1; iCont < strCpf.length() - 1; iCont++) {
                    iDigitoCPF = Integer.valueOf(strCpf.substring(iCont - 1, iCont)).intValue();
                    iDigito1Aux = iDigito1Aux + (11 - iCont) * iDigitoCPF;
                    iDigito2Aux = iDigito2Aux + (12 - iCont) * iDigitoCPF;
                }
                iRestoDivisao = (iDigito1Aux % 11);
                if (iRestoDivisao < 2) {
                    iDigito1 = 0;
                } else {
                    iDigito1 = 11 - iRestoDivisao;
                }
                iDigito2Aux += 2 * iDigito1;
                iRestoDivisao = (iDigito2Aux % 11);
                if (iRestoDivisao < 2) {
                    iDigito2 = 0;
                } else {
                    iDigito2 = 11 - iRestoDivisao;
                }
                strDigitoVerificador = strCpf.substring(strCpf.length() - 2, strCpf.length());
                strDigitoResultado = String.valueOf(iDigito1) + String.valueOf(iDigito2);
                strDigitoVerificador.equals(strDigitoResultado);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }



    @Override
    public String cnpjValidations(String cnpj) {
        if(registerRepository.findByCNPJ(cnpj).isPresent())
            throw new InvalidInputsException("Já existe um cadastro desse CNPJ como chave pix.");

        if(cnpj.length()!=14)
            throw new InvalidInputsException("A quantidade de caracteres do CNPJ deve ser igual a 14.");

        if(!validCnpjCalculations(cnpj))
            throw new InvalidInputsException("Esse CNPJ é inválido.");

        return null;
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
    public String emailValidations(String email) {
        //1 - Checar se já existe um valor cadastrado. Caso contrario jogar exception.
        if (registerRepository.findByEmail(email).isPresent())
            throw new InvalidInputsException("Já existe esse email cadastrado como chave pix.");

        if (email.length() > 77)
            throw new InvalidInputsException("A quantidade de caracteres do e-mail deve ser inferior ou igual a 77.");


        return null;
    }

    @Override
    public boolean accountTypeValid(String accountType) {
        if (Objects.isNull(accountType)||accountType.isBlank())
            throw new InvalidInputsException("O tipo da conta não pode ser vazio nem nulo.");
        if(!accountType.equalsIgnoreCase("corrente")||!accountType.equalsIgnoreCase("poupança"))
            throw new InvalidInputsException("O tipo da conta deve ser ou CORRENTE ou POUPANÇA.");

        return true;
    }
}
