package ogjg.instagram.hashtag.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Hashtag {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "hashtag")
    private List<HashtagFeed> hashtagFeeds;

    @Column(length = 150, nullable = false)
    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @Builder
    public Hashtag(Long id, List<HashtagFeed> hashtagFeeds, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.hashtagFeeds = hashtagFeeds;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
