package com.noua.accounts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {

    @NotEmpty(message = "Name cannot be null or empty")
    @Size(min = 5, max = 50, message = "Name must be between 5 and 30 characters")
    private String name;

    @NotEmpty(message = "Email cannot be null or empty")
    @Email(message = "Email must be a valid email address")
    private String email;

    @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @JsonProperty("accountInfo")
    private AccountsDto accountsDto;
}
