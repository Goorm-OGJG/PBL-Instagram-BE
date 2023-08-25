package ogjg.instagram.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthNumberVerificationDto {
    private String username;
    private String type;
    private String validate;
}
