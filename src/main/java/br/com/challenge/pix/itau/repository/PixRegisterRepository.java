package br.com.challenge.pix.itau.repository;

import br.com.challenge.pix.itau.entity.PixRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PixRegisterRepository extends JpaRepository<PixRegister, String> {
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
}
