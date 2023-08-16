package ogjg.instagram.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter @Setter
@RequiredArgsConstructor
public class ErrorResponseDto implements Serializable {
    private final HttpStatus errorType;
    private final String message;
}
