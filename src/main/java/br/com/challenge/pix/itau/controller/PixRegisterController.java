package br.com.challenge.pix.itau.controller;

import br.com.challenge.pix.itau.dto.PixRegisterRequest;
import br.com.challenge.pix.itau.dto.PixRegisterResponse;
import br.com.challenge.pix.itau.dto.UUIDRegisterDTO;

import br.com.challenge.pix.itau.services.PixRegisterServices;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
public class PixRegisterController implements PixRegisterAPI{

    private final PixRegisterServices services;


    @Override
    public ResponseEntity<UUIDRegisterDTO> createPixRegister(PixRegisterRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(services.createRegister(request));
    }

    @Override
    public ResponseEntity<PixRegisterResponse> deactivateRegister(String registerId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(services.deactivateRegister(registerId));
    }

    @Override
    public ResponseEntity<PixRegisterResponse> findRegisterById(String registerId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(services.findRegisterById(registerId));
    }

    @Override
    public ResponseEntity<Page<PixRegisterResponse>> filterRegisters(
            Integer page,
            Integer size,
            String keyType,
            Integer agencyNumber,
            Integer accountNumber,
            String userFirstName,
            Date createdAt,
            Date deletedAt
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(services.findRegistersFiltered(
                        page,
                        size,
                        keyType,
                        agencyNumber,
                        accountNumber,
                        userFirstName,
                        createdAt,
                        deletedAt
                ));
    }


}
