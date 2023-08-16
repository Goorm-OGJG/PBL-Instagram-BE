package ogjg.instagram.story.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ogjg.instagram.story.dto.request.StoryMediaSaveDto;

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
}


