package br.com.challenge.pix.itau.services;

import br.com.challenge.pix.itau.exceptions.InvalidInputsException;
import br.com.challenge.pix.itau.repository.PixRegisterRepository;
import org.junit.jupiter.api.*;

import org.mockito.Mock;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ValidationServiceImplTest {

    @Mock
    private PixRegisterRepository repository;
    private ValidationServiceImpl underTest;
    private static final String USER_FIRST_NAME_EMPTY_FAIL_CASE = "";

    private static final String KEY_TYPE_FAIL_CASE = "FAIL";

//    @BeforeEach
//    void setUp(){
//        autoCloseable = MockitoAnnotations.openMocks(this);
//        underTest = new ValidationServiceImpl(registerRepository);
//        ReflectionTestUtils.setField(underTest,
//                "REGEX_EMAIL"
//                , "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
//    }
    @BeforeAll
    void setUp(){
        this.underTest = new ValidationServiceImpl(repository);
    }
//    @AfterEach
//    void tearDown() throws Exception {
//        autoCloseable.close();
//    }

    @Test
    void agencyNumberValidEmptyCaseFailCase(){

        assertThrows(InvalidInputsException.class, () -> underTest.agencyNumberValid(null));
    }
    @Test
    void agencyNumberValidLengthCaseFailCase(){

        assertThrows(InvalidInputsException.class, () -> underTest.agencyNumberValid(12345));
    }
    @Test
    void accountNumberValidEmptyFailCase(){

        assertThrows(InvalidInputsException.class, () -> underTest.accountNumberValid(null));
    }
    @Test
    void accountNumberValidLengthFailCase(){

        assertThrows(InvalidInputsException.class, () -> underTest.accountNumberValid(123456789));
    }
    @Test
    void accountTypeValidFailCase(){

        assertThrows(InvalidInputsException.class, () -> underTest.accountTypeValid("Tipo inválido."));
    }
    @Test
    void accountTypeValidEmptyFailCase(){

        assertThrows(InvalidInputsException.class, () -> underTest.accountTypeValid(null));
    }
    @Test
    void validateUserFirstNameFailCaseLength() {

        assertThrows(InvalidInputsException.class, () -> underTest.validateUserFirstName("Este nome vai jogar uma exception porque tem mais de 45 caracteres."));
    }

    @Test
    void validateUserFirstNameFailCaseEmptyName(){
        assertThrows(InvalidInputsException.class, () -> underTest.validateUserFirstName(""));
    }
    @Test
    void validateUserLastNameFailCase() {

        assertThrows(InvalidInputsException.class, () -> underTest.validateUserLastName("Este nome vai jogar uma exception porque tem mais de 45 caracteres."));
    }

    @Test
    void validateRequest() {
    }

    @Test
    void validatePatchRequest() {
    }

    @Test
    void keyTypeValidationFailCase() {
        ValidationService underTest = new ValidationServiceImpl(repository);
        assertThrows(InvalidInputsException.class,() -> underTest.keyTypeValidation(KEY_TYPE_FAIL_CASE));
    }


    @Test
    void cpfValidations() {
    }

    @Test
    void cnpjValidations() {
    }

    @Test
    void emailValidations() {
    }

    @Test
    void accountTypeValid() {
    }

    @Test
    void accountNumberValid() {
    }

    @Test
    void agencyNumberValid() {
    }
}