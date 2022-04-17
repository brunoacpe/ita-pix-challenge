package br.com.challenge.pix.itau.services;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import br.com.challenge.pix.itau.dto.PixRegisterRequest;
import br.com.challenge.pix.itau.entity.PixRegister;
import br.com.challenge.pix.itau.exceptions.InvalidInputsException;
import br.com.challenge.pix.itau.repository.PixRegisterRepository;
import org.junit.jupiter.api.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


class ValidationServiceImplTest {

    @Mock
    private PixRegisterRepository repository;

    @InjectMocks
    private ValidationServiceImpl underTest;



    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(
                underTest,
                "REGEX_EMAIL"
                , "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    }



    @Test
    void agencyNumberValidEmptyCaseFailCase(){
        assertThatThrownBy(()  -> underTest.agencyNumberValid(null))
                .isInstanceOf(InvalidInputsException.class);
    }
    @Test
    void agencyNumberValidLengthCaseFailCase(){

        assertThatThrownBy(() -> underTest.agencyNumberValid(12345))
                .isInstanceOf(InvalidInputsException.class);
    }
    @Test
    void accountNumberValidEmptyFailCase(){
        assertThatThrownBy(() -> underTest.accountNumberValid(null))
                .isInstanceOf(InvalidInputsException.class);
    }
    @Test
    void accountNumberValidLengthFailCase(){

        assertThatThrownBy(() -> underTest.accountNumberValid(123456789))
                .isInstanceOf(InvalidInputsException.class);
    }

    @Test
    void accountTypeValidFailCase(){

        assertThatThrownBy(() -> underTest.accountTypeValid("Tipo inválido."))
                .isInstanceOf(InvalidInputsException.class);
    }
    @Test
    void accountTypeValidEmptyFailCase(){

        assertThatThrownBy(() -> underTest.accountTypeValid(null)).isInstanceOf(InvalidInputsException.class);
    }
    @Test
    void validateUserFirstNameFailCaseLength() {

        assertThatThrownBy(() -> underTest.validateUserFirstName("Este nome vai jogar uma exception porque tem mais de 45 caracteres."))
                .isInstanceOf(InvalidInputsException.class);
    }

    @Test
    void validateUserFirstNameFailCaseEmptyName(){
        assertThatThrownBy(() -> underTest.validateUserFirstName(""))
                .isInstanceOf(InvalidInputsException.class);
    }
    @Test
    void validateUserLastNameFailCase() {

        assertThatThrownBy(() -> underTest.validateUserLastName("Este nome vai jogar uma exception porque tem mais de 45 caracteres."))
                .isInstanceOf(InvalidInputsException.class);
    }

    @Test
    void validateRequestWithKeyTypeEmail() {
        PixRegisterRequest request = new PixRegisterRequest(
                "email",
                "teste.bruno@gmail.com",
                "corrente",
                1101,
                12312,
                "Bruno",
                "Pacheco"
        );
        assertDoesNotThrow(() -> underTest.validateRequest(request));
    }

    @Test
    void validateRequestWithKeyTypeCpf() {
        PixRegisterRequest request = new PixRegisterRequest(
                "cpf",
                "57365807806",
                "corrente",
                1101,
                12312,
                "Bruno",
                "Pacheco"
        );
        assertDoesNotThrow(() -> underTest.validateRequest(request));
    }

    @Test
    void validateRequestWithKeyTypeCnpj() {
        PixRegisterRequest request = new PixRegisterRequest(
                "cnpj",
                "54306804000142",
                "corrente",
                1101,
                12312,
                "Bruno",
                "Pacheco"
        );
        assertDoesNotThrow(() -> underTest.validateRequest(request));
    }
    @Test
    void validatePatchRequest(){
        PixRegisterRequest request = new PixRegisterRequest(
                "cnpj",
                "54306804000142",
                "corrente",
                1101,
                12312,
                "Bruno",
                "Pacheco"
        );

        assertDoesNotThrow(() -> underTest.validatePatchRequest(request));
    }

    @Test
    void keyTypeValidationFailCase() {
        assertThatThrownBy(()-> underTest.keyTypeValidation("TESTE FALHA"))
                .isInstanceOf(InvalidInputsException.class);
    }


    @Test
    void willThrowExceptionIfCpfAlreadyUsed() {
        String cpf = "57365807806";
        given(repository.findByCPF(cpf))
                .willReturn(Optional.of(new PixRegister()));

        assertThatThrownBy(() -> underTest.cpfValidations(cpf))
                .isInstanceOf(InvalidInputsException.class);
    }

    @Test
    void willThrowExceptionWhenCpfIsInvalid() {
        String invalidCpf = "532132123454";
        assertThatThrownBy(() -> underTest.cpfValidations(invalidCpf))
                .isInstanceOf(InvalidInputsException.class);
    }

    @Test
    void assertCpfValidatorWillThrowExceptionInInvalidCpfs(){
        CPFValidator validator = new CPFValidator();
        String invalidCpf = "532132123454";
        assertThatThrownBy(() -> validator.assertValid(invalidCpf))
                .isInstanceOf(InvalidStateException.class);
    }
    @Test
    void willThrowExceptionWhenCnpjAlreadyUsed() {
        String cnpj = "49432603000151";
        given(repository.findByCNPJ(cnpj))
                .willReturn(Optional.of(new PixRegister()));

        assertThatThrownBy(() -> underTest.cnpjValidations(cnpj))
                .isInstanceOf(InvalidInputsException.class);
    }

    @Test
    void willThrowExceptionWhenCnpjIsInvalid() {
        String invalidCnpj = "12321234512412353123532";
        assertThatThrownBy(() -> underTest.cnpjValidations(invalidCnpj))
                .isInstanceOf(InvalidInputsException.class);

    }

    @Test
    void assertCnpjValidatorWillThrowExceptionInInvalidCnpj(){
        String invalidCnpj = "12321234512412353123532";
        CNPJValidator validator = new CNPJValidator();
        assertThatThrownBy(() -> validator.assertValid(invalidCnpj))
                .isInstanceOf(InvalidStateException.class);
    }
    @Test
    void willThrowWhenEmailAlreadyExists() {
        String email = "test@gmail.com";
        given(repository.findByEmail(email))
                .willReturn(Optional.of(new PixRegister()));

        assertThatThrownBy(() -> underTest.emailValidations(email))
                .isInstanceOf(InvalidInputsException.class);
    }

    @Test
    void willThrowWhenEmailLengthGreaterThan77Characters(){
        String email = "Essa string vai ter mais de 77 caracteres e por isso vai jogar acabar jogando uma exception.";
        assertThatThrownBy(() -> underTest.emailValidations(email))
                .isInstanceOf(InvalidInputsException.class);
    }
    @Test
    void shouldThrowWhenPfAccountHasMoreThan5Registers(){
        PixRegister pixRegister = PixRegister.of(new PixRegisterRequest(
                "cpf",
                "57365807806",
                "corrente",
                1101,
                12312,
                "Bruno",
                "Pacheco"
        ));
        Integer accountNumber = 1111;
        Integer agencyNumber = 2222;
        String accountType = "corrente";
        List<PixRegister> registerList = new ArrayList<>();
        registerList.add(pixRegister);
        registerList.add(pixRegister);
        registerList.add(pixRegister);
        registerList.add(pixRegister);
        registerList.add(pixRegister);
        when(repository.findRegistersByAccountNumberAndAgencyNumberAndAccountType(
                accountNumber,agencyNumber,accountType
        )).thenReturn(registerList);

        assertThatThrownBy(() -> underTest.validateIfAccountReachedMaxRegisters(accountNumber,agencyNumber,accountType))
                .isInstanceOf(InvalidInputsException.class);
    }

    @Test
    void shouldThrowWhenPjAccountHasMoreThan20Registers(){
        PixRegister pixRegister = PixRegister.of(new PixRegisterRequest(
                "cnpj",
                "32203884000178",
                "corrente",
                1101,
                12312,
                "Bruno",
                "Pacheco"
        ));
        Integer accountNumber = 1111;
        Integer agencyNumber = 2222;
        String accountType = "corrente";
        List<PixRegister> registerList = new ArrayList<>();
        registerList.add(pixRegister);
        registerList.add(pixRegister);
        registerList.add(pixRegister);
        registerList.add(pixRegister);
        registerList.add(pixRegister);
        registerList.add(pixRegister);
        registerList.add(pixRegister);
        registerList.add(pixRegister);
        registerList.add(pixRegister);
        registerList.add(pixRegister);
        registerList.add(pixRegister);
        registerList.add(pixRegister);
        registerList.add(pixRegister);
        registerList.add(pixRegister);
        registerList.add(pixRegister);
        registerList.add(pixRegister);
        registerList.add(pixRegister);
        registerList.add(pixRegister);
        registerList.add(pixRegister);
        registerList.add(pixRegister);
        when(repository.findRegistersByAccountNumberAndAgencyNumberAndAccountType(
                accountNumber,agencyNumber,accountType
        )).thenReturn(registerList);

        assertThatThrownBy(() -> underTest.validateIfAccountReachedMaxRegisters(accountNumber,agencyNumber,accountType))
                .isInstanceOf(InvalidInputsException.class);
    }

}