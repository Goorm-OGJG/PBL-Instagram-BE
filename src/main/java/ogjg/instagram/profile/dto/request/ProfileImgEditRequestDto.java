package ogjg.instagram.profile.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static lombok.AccessLevel.PROTECTED;

@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
public class ProfileImgEditRequestDto {
    private String imgUrl;
}
