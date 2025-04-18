package com.noua.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(
        name = "Error Response",
        description = "Schema to hold Error response information"
)
public class ErrorResponseDto {

    @Schema(
            description = "Api path invoked by client"
    )
    private String apiPath;

    @Schema(
            description = "Error code representing the error happened"
    )
    private HttpStatus errorCode;
    @Schema(
            description = "Error message representing the error happened"
    )
    private String errorMessage;
    @Schema(
            description = "Time representing when error happened"
    )
    private LocalDateTime errorTime;
}
