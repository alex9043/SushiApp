package ru.alex9043.sushiapp.DTO.error;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ErrorMessageDTO {
    private String errorCode;
    private String message;
    private Date timestamp;
}
