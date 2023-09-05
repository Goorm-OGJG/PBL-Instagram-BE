package ogjg.instagram.common.exception;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import ogjg.instagram.common.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(WebRequest request, IllegalArgumentException e) {
        log.error("IllegalArgumentException {}", e.getMessage());
        ErrorResponseDto response = new ErrorResponseDto(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = JwtException.class)
    public ResponseEntity<?> handleJwtException(WebRequest request, Exception e) {
        log.error("[Exception] {}", e.getMessage());
        HashMap<String, String> response = new HashMap<>(Map.of(
                "errorType", "INVALID_REFRESH_TOKEN",
                "message", e.getMessage()
        ));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponseDto> handleAllException(WebRequest request, Exception e) {
        log.error("Exception {}", e.getMessage());
        ErrorResponseDto response = new ErrorResponseDto(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
