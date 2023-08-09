package ogjg.instagram.feed.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class FeedMedia {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Feed feed;

    @Column(nullable = false, length = 10)
    private String mediaType;

    @Column(nullable = false, unique = true)
    private String mediaUrl;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
