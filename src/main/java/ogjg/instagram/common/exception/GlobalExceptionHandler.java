package ogjg.instagram.common.exception;

import lombok.extern.slf4j.Slf4j;
import ogjg.instagram.common.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(WebRequest request, IllegalArgumentException e) {
        log.error("IllegalArgumentException {}", e.getMessage());
        ErrorResponseDto response = new ErrorResponseDto(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponseDto> handleAllException(WebRequest request, Exception e) {
        log.error("Exception {}", e.getMessage());
        ErrorResponseDto response = new ErrorResponseDto(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
