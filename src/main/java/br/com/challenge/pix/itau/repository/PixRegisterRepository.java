package br.com.challenge.pix.itau.repository;

import br.com.challenge.pix.itau.entity.PixRegister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PixRegisterRepository extends JpaRepository<PixRegister, UUID>, JpaSpecificationExecutor<PixRegister> {
    @Query(
            value = "Select * from public.pix_registers pr where pr.key_value = ?1 and pr.key_type = 'email'",
            nativeQuery = true
    )
    Optional<PixRegister> findByEmail(String email);

    @Query(
            value = "Select * from public.pix_registers pr where pr.key_value = ?1 and pr.key_type = 'cpf'",
            nativeQuery = true
    )
    Optional<PixRegister> findByCPF(String cpf);


    @Query(
            value = "Select * from public.pix_registers pr where pr.key_value = ?1 and pr.key_type = 'cnpj'",
            nativeQuery = true
    )
    Optional<PixRegister> findByCNPJ(String cnpj);

    @Query(
            value = "SELECT * from public.pix_registers pr where pr.account_number = ?1 and pr.agency_number = ?2 and pr.account_type = ?3",
            nativeQuery = true
    )
    List<PixRegister> findRegistersByAccountNumberAndAgencyNumberAndAccountType(Integer accountNumber, Integer agencyNumber, String accountType);


    @Query(
            value = "SELECT register from PixRegister register " +
                    "WHERE (:keyType IS NULL OR register.keyType = :keyType) " +
                    "AND (:agencyNumber IS NULL OR register.agencyNumber = :agencyNumber) " +
                    "AND (:accountNumber IS NULL OR register.accountNumber = :accountNumber) " +
                    "AND (:userFirstName IS NULL OR register.userFirstName = :userFirstName) "
    )
    Page<PixRegister> findPixRegistersFiltered(
            @Param("keyType") String keyType,
            @Param("agencyNumber") Integer agencyNumber,
            @Param("accountNumber") Integer accountNumber,
            @Param("userFirstName") String userFirstName,
            Pageable pageable
    );
}
