package com.noua.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Accounts",
        description = "Schema to hold Account information"
)
public class AccountsDto {
    @Schema(
            description = "Account number of bank account",
            example = "3235346565"
    )
    @NotEmpty(message = "Account number cannot be null or empty")
    @Pattern(regexp = "^\\d{10}$", message = "Account number must be 10 digits")
    private Long accountNumber;


    @Schema(
            description = "Account type of bank account",
            example = "Savings"
    )
    @NotEmpty(message = "Account type cannot be null or empty")
    private String accountType;

    @Schema(
            description = "Bank branch address",
            example = "Main Street, New York 123"
    )
    @NotEmpty(message = "Branch address cannot be null or empty")
    private String branchAddress;
}
