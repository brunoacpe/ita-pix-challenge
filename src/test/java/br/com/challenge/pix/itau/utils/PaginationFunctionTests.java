package br.com.challenge.pix.itau.utils;

import br.com.challenge.pix.itau.dto.PixRegisterResponse;
import br.com.challenge.pix.itau.entity.PixRegister;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PaginationFunctionTests {

    private final PaginationFunction underTest = new PaginationFunction();

    @Test
    void shouldPaginateList(){
        PixRegister register = new PixRegister(
                UUID.randomUUID(),
                "email",
                "teste.bruno@gmail.com",
                "corrente",
                1101,
                1232,
                "Bruno",
                "Pacheco",
                new Date(),
                new Date()
        );
        Page<PixRegisterResponse> page =  PaginationFunction.of(List.of(PixRegisterResponse.of(register)), 0, 1);
        assertNotNull(page);
    }
}
