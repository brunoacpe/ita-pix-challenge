package br.com.challenge.pix.itau.repository;

import br.com.challenge.pix.itau.entity.PixRegister;
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

//    @Query(
//            value = "SELECT register FROM PixRegister register "+
//                    "WHERE (:key_type IS NULL OR register.keyType = :key_type) "+
//                    "AND (:agency_number IS NULL OR register.agencyNumber = :agency_number) "+
//                    "AND (:account_number IS NULL OR register.accountNumber = :account_number) "+
//                    "AND (:user_first_name IS NULL OR register.userFirstName = :user_first_name) "+
//                    "AND (cast(:created_at as date) IS NULL OR register.createdAt = :created_at) "+
//                    "AND (cast(:deleted_at as date) IS NULL OR register.deletedAt = :deleted_at)"
//    )
//    List<PixRegister> findPixRegistersFiltering(
//            @Param(value = "key_type") String keyType,
//            @Param(value = "agency_number") Integer agencyNumber,
//            @Param(value = "account_number") Integer accountNumber,
//            @Param(value = "user_first_name") String userFirstName,
//            @Param(value = "created_at") Date createdAt,
//            @Param(value = "deleted_at") Date deletedAt
//    );
}
