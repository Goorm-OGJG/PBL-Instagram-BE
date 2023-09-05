package ogjg.instagram.search.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@NoArgsConstructor
@Getter
public class SearchHashtagResponseDto {
    private boolean isUser;
    private List<SearchHashtagDto> userList;


    @Builder
    public SearchHashtagResponseDto(List<SearchHashtagDto> userList, boolean isUser) {
        this.userList = userList;
        this.isUser = isUser;
    }

    public static SearchHashtagResponseDto from(List<SearchHashtagDto> searchResult, boolean isUser) {
        return SearchHashtagResponseDto.builder()
                .isUser(isUser)
                .userList(searchResult)
                .build();
    }

    @Getter
    public static class SearchHashtagDto {

        private String tagName;
        private Long totalFeedCount;

        @Builder
        public SearchHashtagDto(String tagName, Long totalFeedCount) {
            this.tagName = tagName;
            this.totalFeedCount = totalFeedCount;
        }

        public static SearchHashtagDto of(String content, Long totalFeedCount) {
            return SearchHashtagDto.builder()
                    .tagName(content)
                    .totalFeedCount(totalFeedCount)
                    .build();
        }
    }
}
