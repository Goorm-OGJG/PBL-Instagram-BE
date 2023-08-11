package ogjg.instagram.search.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class SearchResponseDtos {
    boolean isUser;
    List<SearchResponseDto> userList;


    @Builder
    public SearchResponseDtos(List<SearchResponseDto> userList, boolean isUser) {
        this.userList = userList;
        this.isUser = isUser;
    }

    public static SearchResponseDtos from(List<SearchResponseDto> searchResult, boolean isUser) {
        return SearchResponseDtos.builder()
                .isUser(isUser)
                .userList(searchResult)
                .build();
    }
}
