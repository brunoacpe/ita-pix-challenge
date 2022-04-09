package br.com.challenge.pix.itau.controller;

import br.com.challenge.pix.itau.dto.PixRegisterRequest;
import br.com.challenge.pix.itau.dto.PixRegisterResponse;
import br.com.challenge.pix.itau.dto.UUIDRegisterDTO;
import br.com.challenge.pix.itau.entity.PixRegister;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/api/register")
public interface PixRegisterAPI {


    @RequestMapping(
            consumes = "application/json",
            produces = "application/json",
            method = RequestMethod.POST
    )
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<UUIDRegisterDTO> createPixRegister(
            @RequestBody PixRegisterRequest request
    );

    @RequestMapping(
            value = "/{register_id}",
            produces = "application/json",
            method = RequestMethod.DELETE
    )
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<PixRegisterResponse> deactivateRegister(
            @PathVariable(value = "register_id") String registerId
    );

    @RequestMapping(
            value = "/{register_id}",
            produces = "application/json",
            method = RequestMethod.GET
    )
    ResponseEntity<PixRegisterResponse> findRegisterById(
            @PathVariable(value = "register_id") String registerId
    );

    @RequestMapping(
            value = "/filter",
            produces = "application/json",
            method = RequestMethod.GET
    )
    ResponseEntity<Page<PixRegisterResponse>> filterRegisters(
            @RequestParam(required = true) Integer page,
            @RequestParam(required = true) Integer size,
            @RequestParam(required = false) String keyType,
            @RequestParam(required = false) Integer agencyNumber,
            @RequestParam(required = false) Integer accountNumber,
            @RequestParam(required = false) String userFirstName,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date createdAt,
            @RequestParam(required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") Date deletedAt
    );
}
