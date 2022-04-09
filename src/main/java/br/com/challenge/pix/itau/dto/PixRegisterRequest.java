package br.com.challenge.pix.itau.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Size;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PixRegisterRequest {

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

    @JsonProperty(value = "first_name")
    private String userFirstName;

    @JsonProperty(value = "last_name")
    private String userLastName = "";

}
