package br.com.challenge.pix.itau.dto;

import br.com.challenge.pix.itau.entity.PixRegister;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PixRegisterResponsePatch {

    private UUID id;


    @JsonProperty(value = "key_type")
    private String keyType;


    @JsonProperty(value = "key_value")
    private String keyValue;

    @JsonProperty(value = "account_type")
    private String accountType;


    @JsonProperty(value = "agency_number")
    private Integer agencyNumber;


    @JsonProperty(value = "account_number")
    private Integer accountNumber;


    @JsonProperty(value = "user_first_name")
    private String userFirstName;


    @JsonProperty(value = "user_last_name")
    private String userLastName;


    @JsonProperty(value = "created_at")
    private String createdAt;


    public static PixRegisterResponsePatch of (PixRegister register){
        PixRegisterResponsePatch response = new PixRegisterResponsePatch();
        response.setId(register.getId());
        response.setKeyType(register.getKeyType());
        response.setKeyValue(register.getKeyValue());
        response.setAccountType(register.getAccountType());
        response.setAgencyNumber(register.getAgencyNumber());
        response.setAccountNumber(register.getAccountNumber());
        response.setUserFirstName(register.getUserFirstName());
        response.setUserLastName(register.getUserLastName());
        response.setCreatedAt(register.getCreatedAt().toString());
        return response;
    }
}
