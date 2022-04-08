package br.com.challenge.pix.itau.controller;

import br.com.challenge.pix.itau.dto.PixRegisterDTO;
import br.com.challenge.pix.itau.entity.PixRegister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/register")
public interface PixRegisterAPI {


    @RequestMapping(
            consumes = "application/json",
            produces = "application/json",
            method = RequestMethod.POST
    )
    ResponseEntity<PixRegister> createPixRegister(
            @RequestBody PixRegisterDTO request
    );
}
