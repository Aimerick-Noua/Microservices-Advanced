package com.noua.accounts.controller;

import com.noua.accounts.constants.AccountsConstants;
import com.noua.accounts.dto.CustomerDto;
import com.noua.accounts.dto.ErrorResponseDto;
import com.noua.accounts.dto.ResponseDto;
import com.noua.accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "CRUD REST API for Accounts",
        description = "CRUD REST API for Accounts to create, fetch, update and delete accounts"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class AccountsController {
    private final IAccountsService iAccountsService;


    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create a new Customer & Account inside our bank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Http Status Created"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status Internal Server Error"
            )
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
    iAccountsService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));

    }
    @Operation(
            summary = "Fetch Account REST API",
            description = "REST API to fetch Customer & Account inside our bank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status Internal Server Error"
            )
    })
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetChAccountDetails(@RequestParam
                                                               @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
                                                               String  mobileNumber){
     CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
     return ResponseEntity
             .status(HttpStatus.OK)
             .body(customerDto);
    }

    @Operation(
            summary = "Update Account REST API",
            description = "REST API to update Customer & Account inside our bank"
    )
   @ApiResponses({
           @ApiResponse(
                   responseCode = "200",
                   description = "Http Status OK"
           ),
           @ApiResponse(
                   responseCode = "417",
                   description = "Expectation Failed"
           ),
           @ApiResponse(
                   responseCode = "500",
                   description = "Http Status Internal Server Error",
                   content = @Content(
                           schema = @Schema(
                                   implementation = ErrorResponseDto.class
                           )
                   )
           )
   })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccount(@RequestBody @Valid CustomerDto customerDto){
        boolean isUpdated = iAccountsService.updateAccount(customerDto);
        if(isUpdated){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete Account REST API",
            description = "REST API to delete Customer & Account inside our bank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status Internal Server Error"
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam
                                                         @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
                                                         String mobileNumber){
        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
        if(isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
        }
    }


}
