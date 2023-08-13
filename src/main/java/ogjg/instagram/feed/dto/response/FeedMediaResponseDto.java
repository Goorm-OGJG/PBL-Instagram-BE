package ogjg.instagram.feed.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ogjg.instagram.feed.domain.FeedMedia;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class FeedMediaResponseDto {

    private Long mediaId;
    private String mediaType;
    private String mediaUrl;


    public FeedMediaResponseDto(FeedMedia feedMedia) {
        this.mediaId = feedMedia.getId();
        this.mediaType = feedMedia.getMediaType();
        this.mediaUrl = feedMedia.getMediaUrl();
    }
}
