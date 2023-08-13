package ogjg.instagram.feed.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ogjg.instagram.feed.domain.Feed;
import ogjg.instagram.feed.domain.FeedMedia;
import ogjg.instagram.hashtag.domain.Hashtag;
import ogjg.instagram.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@ToString
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
public class FeedCreateRequestDto {

    private String content;
    private List<String> hashtags;
    private List<String> mediaUrls;

    public Feed toFeed(User user) {
        Feed feed = buildFeed(user);
        List<FeedMedia> medias = buildMedias();

        feed.addAllMedias(medias);
        return feed;
    }

    private List<FeedMedia> buildMedias() {
        List<FeedMedia> medias = mediaUrls.stream()
                .map((url) -> FeedMedia.builder()
                        .mediaUrl(url)
                        .mediaType(substringType(url))
                        .modifiedAt(LocalDateTime.now())
                        .createdAt(LocalDateTime.now())
                        .build()
        ).toList();
        return medias;
    }

    private Feed buildFeed(User user) {
        Feed feed = Feed.builder()
                .content(this.content)
                .user(user)
                .modifiedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();
        return feed;
    }

    private String substringType(String url) {
        int lastIndex = url.lastIndexOf(".");
        return url.substring(lastIndex, url.length());
    }

    public List<Hashtag> toHashtags() {
        return hashtags.stream()
                .map((content) -> Hashtag.builder()
                        .content(content)
                        .modifiedAt(LocalDateTime.now())
                        .createdAt(LocalDateTime.now())
                        .build())
                .toList();
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = PROTECTED)
    public static class HashtagsDto {
        private List<String> hashtags;

        public List<String> list() {
            return hashtags;
        }
    }

    @Getter @Setter
    @NoArgsConstructor(access = PROTECTED)
    public static class MediaUrlsDto {
        private List<String> mediaUrls;

        public List<String> list() {
            return mediaUrls;
        }
    }
}
