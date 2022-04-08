package br.com.challenge.pix.itau.controller;

import br.com.challenge.pix.itau.dto.PixRegisterDTO;
import br.com.challenge.pix.itau.entity.PixRegister;
import br.com.challenge.pix.itau.repository.PixRegisterRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PixRegisterController implements PixRegisterAPI{

    private final PixRegisterRepository repository;
    @Override
    public ResponseEntity<PixRegister> createPixRegister(PixRegisterDTO request) {
        return null;
    }
}
