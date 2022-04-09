package br.com.challenge.pix.itau.services;

import br.com.challenge.pix.itau.dto.PixRegisterRequest;
import br.com.challenge.pix.itau.dto.PixRegisterResponse;
import br.com.challenge.pix.itau.dto.UUIDRegisterDTO;
import br.com.challenge.pix.itau.entity.PixRegister;
import br.com.challenge.pix.itau.exceptions.NoRegistersReturnedException;
import br.com.challenge.pix.itau.exceptions.InvalidInputsException;
import br.com.challenge.pix.itau.repository.PixRegisterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PixRegisterServicesImpl implements PixRegisterServices{

    private final PixRegisterRepository repository;

    private final ValidationService validations;


    @Override
    public UUIDRegisterDTO createRegister(PixRegisterRequest request) {
        validations.validateRequest(request);
        PixRegister register = PixRegister.of(request);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        register.setCreatedAt(new Date());
        String uuid = repository
                .save(register)
                .getId()
                .toString();
        return new UUIDRegisterDTO(uuid);
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
    public List<PixRegisterResponse> findRegistersFiltered(
            String keyType,
            Integer agencyNumber,
            Integer accountNumber,
            String userFirstName,
            Date createdAt,
            Date deletedAt
    ) {
        if(createdAt!=null&&deletedAt!=null)
            throw new InvalidInputsException("Não é permitido filtrar com os dois campos de auditoria de data. Apenas um.");
        List<PixRegister> filtedRegisters = repository.
                findPixRegistersFiltering(
                        keyType,
                        agencyNumber,
                        accountNumber,
                        userFirstName,
                        createdAt,
                        deletedAt
                );
        if(filtedRegisters.isEmpty())
            throw new NoRegistersReturnedException("Não foi encontrado nenhum registro com este filtro.");

        return filtedRegisters
                .stream()
                .map(PixRegisterResponse::of)
                .collect(Collectors.toList());
    }
}
