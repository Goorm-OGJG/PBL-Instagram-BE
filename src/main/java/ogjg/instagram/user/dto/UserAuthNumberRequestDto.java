package ogjg.instagram.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class UserAuthNumberRequestDto {
    private String username;
    private String type;

    public UserAuthNumberRequestDto(String username, String type) {
        this.username = username;
        this.type = type;
    }
}
