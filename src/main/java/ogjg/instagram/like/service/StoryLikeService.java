package ogjg.instagram.like.service;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.like.domain.storyLike.StoryLike;
import ogjg.instagram.like.dto.StoryLikeDto;
import ogjg.instagram.like.repository.StoryLikeRepository;
import ogjg.instagram.story.domain.Story;
import ogjg.instagram.story.repository.StoryRepository;
import ogjg.instagram.user.domain.User;
import ogjg.instagram.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoryLikeService {

    private final StoryLikeRepository storyLikeRepository;
    private final UserRepository userRepository;
    private final StoryRepository storyRepository;

    @Transactional
    public void storyLike(Long userId,Long storyId ){
//        todo 토큰 userId로 변경하기
        StoryLikeDto storyLikeDto = new StoryLikeDto(userId,storyId);
        storyLikeRepository.save(new StoryLike(storyLikeDto,UserFindByUserId(userId), StoryFindByStoryId(storyId)));
    }

    @Transactional
    public void storyUnlike(Long storyId, Long userId){
//        todo 토큰 userId로 변경하기
        storyLikeRepository.deleteStoryLike(storyId, userId);
    }

    public Long storyLikeCount(Long storyId){
        return storyLikeRepository.countLikes(storyId);
    }

    private User UserFindByUserId(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException(userId + ": 사용자를 찾을 수 없습니다"));
    }

    private Story StoryFindByStoryId(Long storyId){
        return storyRepository.findById(storyId)
                .orElseThrow(()->new IllegalArgumentException(storyId + ": 스토리를 찾을 수 없습니다"));
    }

}
