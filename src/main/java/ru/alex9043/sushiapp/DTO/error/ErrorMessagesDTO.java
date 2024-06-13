package ru.alex9043.sushiapp.DTO.error;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorMessagesDTO {
    private List<ErrorMessageDTO> errorMessages;
}
