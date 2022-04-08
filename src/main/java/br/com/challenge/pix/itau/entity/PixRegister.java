package br.com.challenge.pix.itau.entity;

import br.com.challenge.pix.itau.dto.PixRegisterDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(
        name = "pix_registers",
        schema = "public"
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PixRegister {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(
            name = "key_type",
            length = 9
    )
    private String keyType;

    @Column(
            name = "key_value",
            length = 77
    )
    private String keyValue;

    @Column(
            name = "account_type",
            length = 10
    )
    private String accountType;

    @Column(
            name = "agency_number"
    )
    private Integer agencyNumber;

    @Column(
            name = "account_number"
    )
    private Integer accountNumber;

    @Column(
            name = "user_first_name",
            length = 30
    )
    private String userFirstName;

    @Column(
            name = "user_last_name",
            length = 45
    )
    private String userLastName;

    @Column(
            name = "created_at"
    )
    private Date createdAt;

    @Column(
            name = "deleted_at"
    )
    private Date deletedAt;

}
