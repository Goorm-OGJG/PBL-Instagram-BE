package ogjg.instagram.story.service;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.like.service.StoryLikeService;
import ogjg.instagram.story.domain.Story;
import ogjg.instagram.story.domain.StoryMedia;
import ogjg.instagram.story.dto.request.StoryMediaListDto;
import ogjg.instagram.story.dto.request.StoryMediaSaveDto;
import ogjg.instagram.story.dto.request.StoryUserReadDto;
import ogjg.instagram.story.dto.response.StoryListDto;
import ogjg.instagram.story.dto.response.StoryMediaDto;
import ogjg.instagram.story.repository.StoryMediaRepository;
import ogjg.instagram.story.repository.StoryReadRepository;
import ogjg.instagram.story.repository.StoryRepository;
import ogjg.instagram.user.domain.StoryUserRead;
import ogjg.instagram.user.domain.User;
import ogjg.instagram.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoryService {

    private final StoryRepository storyRepository;
    private final StoryMediaRepository storyMediaRepository;
    private final StoryLikeService storyLikeService;
    private final StoryReadRepository storyReadRepository;
    private final UserRepository userRepository;

    public List<StoryListDto> storyList(Long userId){
        return storyRepository.storyList().stream().map(
                storyListDto -> storyListDto.putMediaList(
                        storyMediaList(userId,storyListDto.getStoryId())
                )
                .putReadAll(showStoryRead(storyListDto.getStoryId(),userId))
        ).toList();
    }

    @Transactional
    public void storySave(Long userId, StoryMediaListDto storyMedia){
//        todo security 로 내 id받아올 예정
        Story story = storyRepository.save(new Story(findUser(userId)));

        storyMedia.getMediaList().forEach(
                storyMediaUrl -> storyMediaRepository.save(
                        new StoryMedia(
                                new StoryMediaSaveDto(
                                        findStory(story.getId()), storyMediaType(storyMediaUrl), storyMediaUrl
                                )
                        )
                )
        );
    }

    @Transactional
    public void storyDelete(Long storyId, Long userId){
//        todo userId를 통해 권한이 있는지 확인
        storyMediaRepository.mediaDeleteAll(storyId);
        storyRepository.deleteById(storyId);
    }

    @Transactional
    public void storyReadSave(Long storyId,Long userId){
//        todo security 로 내 id받아올 예정
        StoryUserReadDto storyUserRead = new StoryUserReadDto(storyId,userId);

        if(showStoryRead(storyId,userId)){
            storyReadRepository.save(new StoryUserRead(storyUserRead,findUser(userId),findStory(storyId)));
        }
    }

    private String storyMediaType(String mediaUrl){
        int lastIndexDot = mediaUrl.lastIndexOf(".");
        String urlExtension = mediaUrl.substring(lastIndexDot + 1).toLowerCase().trim();

        return switch (urlExtension) {
            case "png", "jpg" -> "img";
            case "mp4" -> "video";
            default -> throw new IllegalArgumentException("확장자가 잘못됐습니다");
        };
    }

    private boolean showStoryRead(Long storyId,Long userId){
//        todo security 로 내 id받아올 예정
        return storyReadRepository.showStoryRead(userId, storyId).isEmpty();
    }

    private List<StoryMediaDto> storyMediaList(Long userId, Long storyId){
        return storyMediaRepository.findAllStoryMedia(storyId)
                .stream()
                .map(storyMediaDto -> storyMediaDto
                        .putLikeStatus(storyLikeService.storyLikeStatus(userId,storyId)))
                .toList();
    }

    private User findUser(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("사용자를 찾을 수 없습니다" + userId));
    }

    private Story findStory(Long storyId){
        return storyRepository.findById(storyId)
                .orElseThrow(()-> new IllegalArgumentException("스토리를 찾을 수 없습니다" + storyId));
    }


}
