package ogjg.instagram.comment.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static lombok.AccessLevel.PROTECTED;

@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
public class CommentCreateRequestDto {
    private String content;
}
