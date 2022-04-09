package br.com.challenge.pix.itau.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UUIDRegisterDTO {

    @JsonProperty(value = "pix_register_uuid")
    private String pixRegisterUUID;
}
