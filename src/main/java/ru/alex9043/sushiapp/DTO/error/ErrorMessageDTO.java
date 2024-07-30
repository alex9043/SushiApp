package ru.alex9043.sushiapp.DTO.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Schema(description = "DTO for error response")
@Data
@Builder
public class ErrorMessageDTO {
    private String errorCode;
    private String message;
    private Date timestamp;
}
