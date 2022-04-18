package br.com.challenge.pix.itau.controller;

import br.com.challenge.pix.itau.dto.PixRegisterRequest;
import br.com.challenge.pix.itau.dto.PixRegisterResponse;
import br.com.challenge.pix.itau.dto.PixRegisterResponsePatch;
import br.com.challenge.pix.itau.dto.UUIDRegisterDTO;

import br.com.challenge.pix.itau.services.PixRegisterServices;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@RestController
@AllArgsConstructor
@Slf4j
public class PixRegisterController implements PixRegisterAPI{

    private final PixRegisterServices services;


    @Override
    public ResponseEntity<UUIDRegisterDTO> createPixRegister(PixRegisterRequest request) {
        log.info("Request POST para mapping /v1/api/register/. Request infos: Body: {}", request.toString());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(services.createRegister(request));
    }

    @Override
    public ResponseEntity<PixRegisterResponse> deactivateRegister(String registerId) {
        log.info("Request DELETE para mapping /v1/api/register/. Request infos: Id do registro para ser desativado: {}", registerId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(services.deactivateRegister(registerId));
    }

    @Override
    public ResponseEntity<PixRegisterResponse> findRegisterById(String registerId) {
        log.info("Request GET para mapping /v1/api/register. Request infos: Id do registro sendo buscado: {}", registerId);
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
            String userFirstName
    ) {
        log.info(
                "Request GET para mapping /v1/api/register/filter. Request infos: page: {}, size {}, keyType: {}," +
                        " agencyNumber: {}, accountNumber: {}, userFirstName: {}",
                page,size,keyType,agencyNumber,accountNumber,userFirstName
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(services.findRegistersFiltered(
                        page,
                        size,
                        keyType,
                        agencyNumber,
                        accountNumber,
                        userFirstName
                ));
    }

    @Override
    public ResponseEntity<PixRegisterResponsePatch> patchPixRegister(String registerId, PixRegisterRequest request) {
        log.info("Request PATCH para mapping /v1/api/register. Request infos: Id do registro para ser atualizado: {}, Body: {}", registerId,request.toString());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(services.patchPixRegister(
                        registerId,
                        request
                ));
    }


}
