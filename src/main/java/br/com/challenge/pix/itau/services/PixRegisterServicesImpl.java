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
import org.springframework.stereotype.Service;


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
        UUID randomUUID = UUID.randomUUID();
        register.setCreatedAt(new Date());
        register.setId(randomUUID);
        repository.save(register);
        log.info("Novo registro inserido no banco. Registro: {}, UUID: {}, Data: {}", request.toString(), randomUUID.toString(), new Date());
        return new UUIDRegisterDTO(randomUUID.toString());
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
            String userFirstName,
            Date createdAt,
            Date deletedAt
    ) {

        if(createdAt!=null&&deletedAt!=null)
            throw new InvalidInputsException("Não é permitido filtrar com os dois campos de auditoria de data. Apenas um.");

        Page<PixRegister> filteredRegisters =  repository.findPixRegistersFiltered(
                keyType,
                agencyNumber,
                accountNumber,
                userFirstName,
                createdAt,
                deletedAt,
                PageRequest.of(page,size, Sort.by(Sort.Order.desc("created_at")))
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
        PixRegister register = repository.findById(UUID.fromString(registerId))
                .orElseThrow(() -> new NoRegistersReturnedException("Não foi encontrado nenhum registro com este ID."));

        if(register.getDeletedAt()!=null)
            throw new InvalidInputsException("Não é possível alterar registros inativados.");

        validations.validatePatchRequest(request);

        register.setAccountType(request.getAccountType());
        register.setAccountNumber(request.getAccountNumber());
        register.setAgencyNumber(request.getAgencyNumber());
        register.setUserFirstName(request.getUserFirstName());
        register.setUserLastName(request.getUserLastName());
        repository.save(register);

        return PixRegisterResponsePatch.of(register);
    }
}
