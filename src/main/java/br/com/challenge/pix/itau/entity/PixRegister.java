package br.com.challenge.pix.itau.entity;

import br.com.challenge.pix.itau.dto.PixRegisterRequest;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;


@Entity
@Table(
        name = "pix_registers",
        schema = "public"
)
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
    private Date createdAt;

    @Column(
            name = "deleted_at"
    )
    @JsonProperty(value = "deleted_at")
    private Date deletedAt;

    public PixRegister(UUID id, String keyType, String keyValue, String accountType, Integer agencyNumber, Integer accountNumber, String userFirstName, String userLastName, Date createdAt, Date deletedAt) {
        this.id = id;
        this.keyType = keyType;
        this.keyValue = keyValue;
        this.accountType = accountType;
        this.agencyNumber = agencyNumber;
        this.accountNumber = accountNumber;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    public PixRegister() {
    }

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

    public String toString() {
        return "PixRegister(id=" + this.getId() + ", keyType=" + this.getKeyType() + ", keyValue=" + this.getKeyValue() + ", accountType=" + this.getAccountType() + ", agencyNumber=" + this.getAgencyNumber() + ", accountNumber=" + this.getAccountNumber() + ", userFirstName=" + this.getUserFirstName() + ", userLastName=" + this.getUserLastName() + ", createdAt=" + this.getCreatedAt() + ", deletedAt=" + this.getDeletedAt() + ")";
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @JsonProperty("key_type")
    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    @JsonProperty("key_value")
    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    @JsonProperty("account_type")
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @JsonProperty("agency_number")
    public void setAgencyNumber(Integer agencyNumber) {
        this.agencyNumber = agencyNumber;
    }

    @JsonProperty("account_number")
    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    @JsonProperty("user_first_name")
    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    @JsonProperty("user_last_name")
    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("deleted_at")
    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public UUID getId() {
        return this.id;
    }

    public String getKeyType() {
        return this.keyType;
    }

    public String getKeyValue() {
        return this.keyValue;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public Integer getAgencyNumber() {
        return this.agencyNumber;
    }

    public Integer getAccountNumber() {
        return this.accountNumber;
    }

    public String getUserFirstName() {
        return this.userFirstName;
    }

    public String getUserLastName() {
        return this.userLastName;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public Date getDeletedAt() {
        return this.deletedAt;
    }
}
