package ogjg.instagram.feed.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ogjg.instagram.user.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Collection {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private User user;

    @OneToMany(mappedBy = "collection")
    private List<CollectionFeed> collectionFeeds = new ArrayList<>();

    private String collectionName;

    private LocalDateTime createdAt;

    @Builder
    public Collection(Long id, User user, List<CollectionFeed> collectionFeeds, String collectionName, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.collectionFeeds = collectionFeeds;
        this.collectionName = collectionName;
        this.createdAt = createdAt;
    }

    public static Collection of(User user, String collectionName) {
        return Collection.builder()
                        .user(user)
                        .createdAt(LocalDateTime.now())
                        .collectionName(collectionName)
                        .build();
    }
}
