package ru.alex9043.sushiapp.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.alex9043.sushiapp.DTO.error.ErrorMessageDTO;
import ru.alex9043.sushiapp.DTO.error.ErrorMessagesDTO;

import java.util.Date;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessagesDTO handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ErrorMessagesDTO.builder()
                .errorMessages(
                        ex.getBindingResult().getFieldErrors().stream().map(
                                error -> ErrorMessageDTO.builder()
                                        .errorCode(HttpStatus.BAD_REQUEST.toString())
                                        .message(error.getDefaultMessage())
                                        .timestamp(new Date())
                                        .build()
                        ).toList()
                )
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessagesDTO handleIllegalArgumentException(IllegalArgumentException ex) {
        return ErrorMessagesDTO.builder()
                .errorMessages(
                        List.of(
                                ErrorMessageDTO.builder()
                                        .errorCode(HttpStatus.NOT_FOUND.toString())
                                        .message(ex.getMessage())
                                        .timestamp(new Date())
                                        .build()
                        )
                )
                .build();
    }
}