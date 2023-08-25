package ogjg.instagram.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserAuthRequestDto {
    private String username;
    private String type;
    private String validate;
    private String password;
}
