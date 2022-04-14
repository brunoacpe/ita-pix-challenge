package br.com.challenge.pix.itau.services;

import br.com.challenge.pix.itau.dto.PixRegisterRequest;
import br.com.challenge.pix.itau.dto.PixRegisterResponse;
import br.com.challenge.pix.itau.dto.UUIDRegisterDTO;
import br.com.challenge.pix.itau.entity.PixRegister;
import br.com.challenge.pix.itau.exceptions.InvalidInputsException;
import br.com.challenge.pix.itau.exceptions.NoRegistersReturnedException;
import br.com.challenge.pix.itau.repository.PixRegisterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

public class PixRegisterServicesTests {
    @Mock
    private PixRegisterRepository repository;
    @Mock
    private ValidationService validations;

    private AutoCloseable autoCloseable;
    @InjectMocks
    private PixRegisterServicesImpl underTest;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void shouldThrowExceptionWhenUUIDNotExists() {
        UUID randomUUID = UUID.randomUUID();
        assertThatThrownBy(() -> underTest.findRegisterById(randomUUID.toString()))
                .isInstanceOf(NoRegistersReturnedException.class);
    }

    @Test
    void createPixRegisterShouldGenerateUUID() {
        PixRegisterRequest pixRegisterRequest = new PixRegisterRequest(
                "email",
                "teste.bruno@gmail.com",
                "corrente",
                1101,
                1231,
                "Bruno",
                "Pacheco"
        );
        when(repository.save(any(PixRegister.class)))
                .then(returnsFirstArg());
        UUIDRegisterDTO savedRegister = underTest.createRegister(pixRegisterRequest);
        assertNotNull(savedRegister);
    }

    @Test
    void shouldThrowExceptionWhenFindByInvalidUUID() {
        UUID random = UUID.randomUUID();
        String id = random.toString();

        when(repository.findById(random))
                .thenThrow(NoRegistersReturnedException.class);

        ;
        assertThatThrownBy(() -> underTest.findRegisterById(id))
                .isInstanceOf(NoRegistersReturnedException.class);
    }

    @Test
    void shouldFindValidUUID() {
        UUID random = UUID.randomUUID();
        String id = random.toString();
        Optional<PixRegister> register = Optional.of(new PixRegister(
                random,
                "email",
                "teste.bruno@gmail.com",
                "corrente",
                1101,
                1232,
                "Bruno",
                "Pacheco",
                new Date(),
                new Date()
        ));
        when(repository.findById(random))
                .thenReturn(register);
        PixRegisterResponse savedRegister = underTest.findRegisterById(id);
        assertNotNull(savedRegister);
    }

    @Test
    void shouldThrowExceptionWhenTryToFilterWithTheTwoDateFields() {
        Integer page = 0;
        Integer size = 10;
        String keyType = "email";
        Date createdAt = new Date();
        Date deletedAt = new Date();
        PixRegister register = new PixRegister();
        when(repository.findPixRegistersFiltered(
                keyType,
                1231,
                12321313,
                "Bruno",
                createdAt,
                deletedAt,
                PageRequest.of(page,size,Sort.by(Sort.Order.desc("created_at")))
        ))
                .thenReturn(new PageImpl<>(List.of(register)));

        assertThatThrownBy(() -> underTest.findRegistersFiltered(page, size, keyType, null, null, null, createdAt, deletedAt))
                .isInstanceOf(InvalidInputsException.class);

    }

    @Test
    void shouldSucssedWhenFiltering(){
        PixRegister register = new PixRegister(
                UUID.randomUUID(),
                "email",
                "teste.bruno@gmail.com",
                "corrente",
                1101,
                1231,
                "Bruno",
                "Pacheco",
                new Date(),
                new Date()
        );
        Integer page = 0;
        Integer size = 10;
        String keyType = "email";
        Date createdAt = new Date();
        when(repository.findPixRegistersFiltered(
                keyType,
                null,
                null,
                null,
                createdAt,
                null,
                PageRequest.of(page,size,Sort.by(Sort.Order.desc("created_at")))
        )).thenReturn(new PageImpl<>(List.of(register)));

        assertNotNull(underTest.findRegistersFiltered(
                page,size,keyType,null,null,null,createdAt,null
        ));
    }
    @Test
    void shouldThrowExceptionIfFilterReturnsEmptyList() {
        Integer page = 0;
        Integer size = 10;
        String keyType = "email";
        Date createdAt = new Date();
        when(repository.findPixRegistersFiltered(
                keyType,
                null,
                null,
                null,
                createdAt,
                null,
                PageRequest.of(page,size,Sort.by(Sort.Order.desc("created_at")))
        )).thenReturn(new PageImpl<>(List.of()));

        assertThatThrownBy(() -> underTest.findRegistersFiltered(
                page, size, keyType, null, null, null, createdAt, null)
        )
                .isInstanceOf(NoRegistersReturnedException.class);
    }

    @Test
    void shouldThrowExceptionPatchPixRegisterWhenUUIDDoesNotExists(){
        UUID uuid = UUID.randomUUID();
        when(repository.findById(uuid))
                .thenThrow(NoRegistersReturnedException.class);

        assertThatThrownBy(() -> underTest.patchPixRegister(uuid.toString(), new PixRegisterRequest()))
                .isInstanceOf(NoRegistersReturnedException.class);
    }

    @Test
    void shouldThrowExceptionPatchPixRegisterWhenRegisterIsDeactivated(){
        PixRegister deactivatedRegister = new PixRegister();
        deactivatedRegister.setDeletedAt(new Date());
        UUID uuid = UUID.randomUUID();
        when(repository.findById(uuid))
                .thenReturn(Optional.of(deactivatedRegister));

        assertThatThrownBy(() -> underTest.patchPixRegister(uuid.toString(), new PixRegisterRequest()))
                .isInstanceOf(InvalidInputsException.class);
    }

    @Test
    void shouldNotThrowExceptionPatchPixRegisterIsValid(){
        UUID uuid = UUID.randomUUID();
        PixRegisterRequest pixRegisterRequest = new PixRegisterRequest(
                "email",
                "teste.bruno@gmail.com",
                "corrente",
                1101,
                1231,
                "Bruno",
                "Pacheco"
        );
        PixRegister register = new PixRegister(
                uuid,
                "email",
                "teste.bruno@gmail.com",
                "corrente",
                1101,
                1231,
                "Bruno",
                "Pacheco",
                new Date(),
                new Date()
        );
        register.setDeletedAt(null);
        when(repository.findById(uuid))
                .thenReturn(Optional.of(register));

        assertNotNull(underTest.patchPixRegister(uuid.toString(),pixRegisterRequest));
    }
    @Test
    void shouldThrowNoRegistersReturnedExceptionWhenDeactivateRegisterUUIDNotExists(){
        UUID id = UUID.randomUUID();

        when(repository.findById(id))
                .thenThrow(NoRegistersReturnedException.class);

        assertThatThrownBy(() -> underTest.deactivateRegister(id.toString()))
                .isInstanceOf(NoRegistersReturnedException.class);
    }

    @Test
    void shouldThrowInvalidInputsExceptionWhenRegisterWasAlreadyDeactivated(){
        UUID id = UUID.randomUUID();
        PixRegister deactivatedRegister = new PixRegister();
        deactivatedRegister.setDeletedAt(new Date());
        when(repository.findById(id))
                .thenReturn(Optional.of(deactivatedRegister));

        assertThatThrownBy(() -> underTest.deactivateRegister(id.toString()))
                .isInstanceOf(InvalidInputsException.class);
    }

    @Test
    void shouldSucssedDeactivatePixRegister(){
        UUID id = UUID.randomUUID();
        PixRegister register = new PixRegister(
                id,
                "email",
                "teste.bruno@gmail.com",
                "corrente",
                1101,
                1231,
                "Bruno",
                "Pacheco",
                new Date(),
                null
        );

        when(repository.findById(id))
                .thenReturn(Optional.of(register));

        assertNotNull(underTest.deactivateRegister(id.toString()));
    }
}
