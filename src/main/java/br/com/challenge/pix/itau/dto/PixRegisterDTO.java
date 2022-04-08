package br.com.challenge.pix.itau.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PixRegisterDTO {


    @JsonProperty(value = "key_value")
    private String keyValue;



    @Size(
            max = 10
    )
    @JsonProperty(value = "account_type")
    private String accountType;


    @JsonProperty(value = "agency_number")
    private Integer agencyNumber;


    @JsonProperty(value = "account_number")
    private Integer accountNumber;

    @Size(
            max = 30,
            message = "O nome primeiro nome do usuário pode ter no máximo 30 caracteres."
    )
    @JsonProperty(value = "first_name")
    private String userFirstName;

    @Size(
            max = 45,
            message = "O nome primeiro nome do usuário pode ter no máximo 45 caracteres."
    )
    @JsonProperty(value = "last_name")
    private String userLastName;
}
