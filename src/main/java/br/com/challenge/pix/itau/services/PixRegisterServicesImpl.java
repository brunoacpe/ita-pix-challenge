package br.com.challenge.pix.itau.services;

import br.com.challenge.pix.itau.dto.PixRegisterRequest;
import br.com.challenge.pix.itau.dto.PixRegisterResponse;
import br.com.challenge.pix.itau.dto.PixRegisterResponsePatch;
import br.com.challenge.pix.itau.dto.UUIDRegisterDTO;
import br.com.challenge.pix.itau.entity.PixRegister;
import br.com.challenge.pix.itau.exceptions.NoRegistersReturnedException;
import br.com.challenge.pix.itau.exceptions.InvalidInputsException;
import br.com.challenge.pix.itau.repository.PixRegisterRepository;
import br.com.challenge.pix.itau.utils.PaginationFunction;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class PixRegisterServicesImpl implements PixRegisterServices{

    private final PixRegisterRepository repository;

    private final ValidationService validations;

    @Override
    public UUIDRegisterDTO createRegister(PixRegisterRequest request) {
        validations.validateRequest(request);
        PixRegister register = PixRegister.of(request);
        register.setCreatedAt(new Date());
        String generatedUUID   = repository
                    .save(register)
                    .getId()
                    .toString();
        log.info("Novo registro inserido no banco. Registro: {}, UUID: {}, Data: {}", request.toString(),generatedUUID , new Date());
        return new UUIDRegisterDTO(generatedUUID);
    }

    @Override
    public PixRegisterResponse deactivateRegister(String registerId) {
        PixRegister register = repository
                    .findById(UUID.fromString(registerId))
                    .orElseThrow(
                            () -> new NoRegistersReturnedException("Não foi encontrado nenhum registro com este ID.")
                    );

        if(register.getDeletedAt()!=null)
            throw new InvalidInputsException("Este registro já foi desativado.");

        register.setDeletedAt(new Date());
        repository.save(register);
        log.info("Registro desativado. Registro: {}, Data: {}", register.toString(), new Date());
        return PixRegisterResponse.of(register);
    }

    @Override
    public PixRegisterResponse findRegisterById(String registerId) {
        PixRegister register = repository
                .findById(UUID.fromString(registerId))
                .orElseThrow(
                        () -> new NoRegistersReturnedException("Não foi encontrado nenhum registro com este ID.")
                );
        return PixRegisterResponse.of(register);
    }

    @Override
    public Page<PixRegisterResponse> findRegistersFiltered(
            Integer page,
            Integer size,
            String keyType,
            Integer agencyNumber,
            Integer accountNumber,
            String userFirstName
    ) {


        Page<PixRegister> filteredRegisters =  repository.findPixRegistersFiltered(
                keyType,
                agencyNumber,
                accountNumber,
                userFirstName,
                PageRequest.of(page,size, Sort.by(Sort.Order.desc("createdAt")))
        );;

        if(filteredRegisters.isEmpty())
            throw new NoRegistersReturnedException("Não foi encontrado nenhum registro com este filtro.");

        List<PixRegisterResponse> responses = filteredRegisters
                .stream()
                .map(PixRegisterResponse::of)
                .collect(Collectors.toList());

        return PaginationFunction.of(responses,page,size);
    }

    @Override
    public PixRegisterResponsePatch patchPixRegister(String registerId, PixRegisterRequest request) {
        validations.validatePatchRequest(request);
        PixRegister register = repository.findById(UUID.fromString(registerId))
                .orElseThrow(() -> new NoRegistersReturnedException("Não foi encontrado nenhum registro com este ID."));

        if(register.getDeletedAt()!=null)
            throw new InvalidInputsException("Não é possível alterar registros inativados.");
        register.setAccountType(request.getAccountType());
        register.setAccountNumber(request.getAccountNumber());
        register.setAgencyNumber(request.getAgencyNumber());
        register.setUserFirstName(request.getUserFirstName());
        register.setUserLastName(request.getUserLastName());
        repository.save(register);
        log.info("Registro atualizado com sucesso. Id: {}, Registro: {}", register.getId().toString(),register.toString());
        return PixRegisterResponsePatch.of(register);
    }
}
