package ogjg.instagram.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {

    @Email
    private String email;
    @NotBlank
    private String username;
    @NotNull
    private String nickname;
    @NotNull
    private String password;

}
