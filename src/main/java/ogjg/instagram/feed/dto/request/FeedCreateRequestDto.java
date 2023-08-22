package ogjg.instagram.feed.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import ogjg.instagram.feed.domain.Feed;
import ogjg.instagram.feed.domain.FeedMedia;
import ogjg.instagram.hashtag.domain.Hashtag;
import ogjg.instagram.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Slf4j
@ToString
@Getter @Setter
@NoArgsConstructor(access = PROTECTED)
public class FeedCreateRequestDto {

    private String content;
    private List<String> hashtags;
    private List<String> mediaUrls;

    public Feed toFeed(User user) {
        Feed feed = buildFeed(user);
        List<FeedMedia> medias = buildMedias(feed);

        feed.addAllMedias(medias);
        return feed;
    }

    private List<FeedMedia> buildMedias(Feed feed) {
        List<FeedMedia> medias = mediaUrls.stream()
                .map((url) -> FeedMedia.builder()
                        .feed(feed)
                        .mediaUrl(url)
                        .mediaType(typeOf(substringType(url)))
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

    private static String typeOf(String urlExtension) {
        return switch (urlExtension.toLowerCase()) {
            case "png", "jpg" -> "img";
            case "mp4" -> "video";
            default -> throw new IllegalArgumentException("확장자가 잘못됐습니다");
        };
    }

    private String substringType(String url) {
        int lastIndex = url.lastIndexOf(".");
        return url.substring(lastIndex + 1, url.length());
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
