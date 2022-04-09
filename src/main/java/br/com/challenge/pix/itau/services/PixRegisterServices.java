package br.com.challenge.pix.itau.services;

import br.com.challenge.pix.itau.dto.PixRegisterRequest;
import br.com.challenge.pix.itau.dto.PixRegisterResponse;
import br.com.challenge.pix.itau.dto.UUIDRegisterDTO;
import br.com.challenge.pix.itau.entity.PixRegister;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface PixRegisterServices {

    UUIDRegisterDTO createRegister(PixRegisterRequest request);

    PixRegisterResponse deactivateRegister(String registerId);

    PixRegisterResponse findRegisterById(String registerId);

    Page<PixRegisterResponse> findRegistersFiltered(
            Integer page,
            Integer size,
            String keyType,
            Integer agencyNumber,
            Integer accountNumber,
            String userFirstName,
            Date createdAt,
            Date deletedAt
    );
}
