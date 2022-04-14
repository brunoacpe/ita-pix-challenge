package br.com.challenge.pix.itau.entity;

import br.com.challenge.pix.itau.dto.PixRegisterRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
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
    @JsonProperty(value = "key_type")
    private String keyType;

    @Column(
            name = "key_value",
            length = 77
    )
    @JsonProperty(value = "key_value")
    private String keyValue;

    @Column(
            name = "account_type",
            length = 10
    )
    @JsonProperty(value = "account_type")
    private String accountType;

    @Column(
            name = "agency_number"
    )
    @JsonProperty(value = "agency_number")
    private Integer agencyNumber;

    @Column(
            name = "account_number"
    )
    @JsonProperty(value = "account_number")
    private Integer accountNumber;

    @Column(
            name = "user_first_name",
            length = 30
    )
    @JsonProperty(value = "user_first_name")
    private String userFirstName;

    @Column(
            name = "user_last_name",
            length = 45
    )
    @JsonProperty(value = "user_last_name")
    private String userLastName;

    @Column(
            name = "created_at"
    )
    @JsonProperty(value = "created_at")
    private Date createdAt;//nao

    @Column(
            name = "deleted_at"
    )
    @JsonProperty(value = "deleted_at")
    private Date deletedAt;//nao

    public static PixRegister of(PixRegisterRequest dto){
        PixRegister register = new PixRegister();
        register.setKeyType(dto.getKeyType());
        register.setKeyValue(dto.getKeyValue());
        register.setAccountType(dto.getAccountType());
        register.setAgencyNumber(dto.getAgencyNumber());
        register.setAccountNumber(dto.getAccountNumber());
        register.setUserFirstName(dto.getUserFirstName());
        register.setUserLastName(dto.getUserLastName());
        return register;
    }
}
