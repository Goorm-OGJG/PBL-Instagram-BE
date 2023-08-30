package ogjg.instagram.search.dto;

import lombok.Getter;

@Getter
public class SearchHashTagCountDto {
    private Long feedId;
    private Long likeCount;
    private Long commentCount;

    public SearchHashTagCountDto(Long feedId, Long likeCount, Long commentCount) {
        this.feedId = feedId;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }
}
