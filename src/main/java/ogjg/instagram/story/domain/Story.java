package ogjg.instagram.story.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ogjg.instagram.like.domain.StoryLike;
import ogjg.instagram.user.domain.StoryUserRead;
import ogjg.instagram.user.domain.User;
import org.hibernate.validator.constraints.Currency;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Story {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private User user;

    @OneToMany(mappedBy = "story")
    private List<StoryLike> storyLikes = new ArrayList<>();

    @OneToMany(mappedBy = "story")
    private List<StoryUserRead> storyUserReads = new ArrayList<>();

    @OneToMany(mappedBy = "story")
    private List<StoryMedia> storyMedia = new ArrayList<>();

    private LocalDateTime createdAt;
}
