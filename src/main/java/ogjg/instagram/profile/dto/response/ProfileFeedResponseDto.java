package ogjg.instagram.profile.dto.response;

import lombok.Getter;
import ogjg.instagram.profile.dto.ProfileFeedDto;
import ogjg.instagram.user.domain.CollectionFeed;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;


@Getter
public class ProfileFeedResponseDto {
    private List<ProfileFeedDto> feedList;
    private boolean isLast;

    protected ProfileFeedResponseDto(List<ProfileFeedDto> feedList) {
        this.feedList = feedList;
    }

    public static ProfileFeedResponseDto by(Page<ProfileFeedDto> feedPage) {
        List<ProfileFeedDto> feedList = feedPage.getContent().stream()
        .collect(Collectors.toList());

        ProfileFeedResponseDto responseDto = new ProfileFeedResponseDto(feedList);

        responseDto.isLast = feedPage.isLast();
        return responseDto;
    }

    public static ProfileFeedResponseDto of(Page<CollectionFeed> savedFeedPage) {
        List<ProfileFeedDto> feedList = savedFeedPage.getContent()
                .stream()
                .map((savedFeed) -> new ProfileFeedDto(savedFeed))
                .collect(Collectors.toUnmodifiableList());

        ProfileFeedResponseDto responseDto = new ProfileFeedResponseDto(feedList);

        responseDto.isLast = savedFeedPage.isLast();
        return responseDto;
    }
}