package ogjg.instagram.story.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import ogjg.instagram.story.dto.response.QStoryListDto;
import ogjg.instagram.story.dto.response.StoryListDto;

import java.time.LocalDateTime;
import java.util.List;

import static ogjg.instagram.story.domain.QStory.story;
import static ogjg.instagram.user.domain.QUser.user;

@RequiredArgsConstructor
public class StoryRepositoryCustomImpl implements StoryRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<StoryListDto> storyList() {

        List<StoryListDto> storyList = jpaQueryFactory.select(new QStoryListDto(
                        story.id.as("storyId"),
                        story.user.id.as("userId"),
                        user.nickname.as("nickname"),
                        user.userImg.as("profileImg"),
                        story.createdAt.as("createdAt")
                )).from(story)
                .join(user)
                .on(story.user.id.eq(user.id))
                .where(story.createdAt.between(LocalDateTime.now().minusHours(24),LocalDateTime.now()))
                .orderBy(story.createdAt.desc())
                .fetch();

        JPAQuery<Long> storyCount = jpaQueryFactory.select(story
                        .count())
                        .from(story);

        return storyList;
    }

    @Override
    public Long storyListCount() {
        return jpaQueryFactory.select(story
                        .count())
                .from(story).fetchOne();
    }
}
