package ru.alex9043.sushiapp.DTO.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Schema(description = "DTO for errors response")

@Data
@Builder
public class ErrorMessagesDTO {
    private List<ErrorMessageDTO> errorMessages;
}
