package ogjg.instagram.story.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ogjg.instagram.story.dto.request.StoryMediaSaveDto;
import org.hibernate.validator.constraints.Currency;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class StoryMedia {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Story story;

    @Column(length = 10, nullable = false)
    private String mediaType;

    @Column(nullable = false, unique = true)
    private String mediaUrl;

    private LocalDateTime createdAt;

    public StoryMedia(StoryMediaSaveDto story) {
        this.story = story.getStory();
        this.mediaType = story.getMediaType();
        this.mediaUrl = story.getMediaUrl();
        this.createdAt = LocalDateTime.now();
    }

//todo 나중에 삭제할 것
    @Builder
    public StoryMedia(Story story, String mediaType, String mediaUrl, LocalDateTime createdAt) {
        this.story = story;
        this.mediaType = mediaType;
        this.mediaUrl = mediaUrl;
        this.createdAt = createdAt;
    }
}


