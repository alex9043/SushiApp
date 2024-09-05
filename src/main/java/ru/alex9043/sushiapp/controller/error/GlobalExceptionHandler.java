package ru.alex9043.sushiapp.controller.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessagesDTO handleValidationExceptions(MethodArgumentNotValidException ex) {
        getLog(ex.getStackTrace(), ex.getMessage());
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

    private void getLog(StackTraceElement[] stackTrace2, String message) {
        if (stackTrace2.length > 0) {
            StackTraceElement element = stackTrace2[0];
            String className = element.getClassName();
            String methodName = element.getMethodName();
            log.error("Error in {}.{} - {}", className, methodName, message);
        } else {
            log.error("Error - {}", message);
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessagesDTO handleIllegalArgumentException(IllegalArgumentException ex) {
        getLog(ex.getStackTrace(), ex.getMessage());
        return ErrorMessagesDTO.builder()
                .errorMessages(
                        List.of(
                                ErrorMessageDTO.builder()
                                        .errorCode(HttpStatus.BAD_REQUEST.toString())
                                        .message(ex.getMessage())
                                        .timestamp(new Date())
                                        .build()
                        )
                )
                .build();
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessagesDTO handleUsernameNotFoundException(UsernameNotFoundException ex) {
        getLog(ex.getStackTrace(), ex.getMessage());
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

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorMessagesDTO handleOtherExceptions(Exception ex) {
        getLog(ex.getStackTrace(), ex.getMessage());
        return ErrorMessagesDTO.builder()
                .errorMessages(
                        List.of(
                                ErrorMessageDTO.builder()
                                        .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                                        .message("Internal server error")
                                        .timestamp(new Date())
                                        .build()
                        )
                )
                .build();
    }
}