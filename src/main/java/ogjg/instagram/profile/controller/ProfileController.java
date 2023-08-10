package ogjg.instagram.profile.controller;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.feed.service.FeedService;
import ogjg.instagram.follow.service.FollowService;
import ogjg.instagram.profile.dto.response.ProfileEditResponseDto;
import ogjg.instagram.profile.dto.response.ProfileFeedResponseDto;
import ogjg.instagram.profile.dto.request.ProfileImgEditRequestDto;
import ogjg.instagram.profile.dto.response.ProfileResponseDto;
import ogjg.instagram.profile.service.ProfileService;
import ogjg.instagram.user.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//todo : profile 서비스로 통합 고려

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController {

    private final UserService userService;
    private final FollowService followService;
    private final FeedService feedService;
    private final ProfileService profileService;

    /**
     * 프로필 가져오기
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> profile(@PathVariable("userId") Long userId) {
        Long jwt_myId = 1L;

        return ResponseEntity.ok(
                ProfileResponseDto.from(
                        userService.findById(userId),
                        feedService.countAllByUserID(userId),
                        followService.followedCount(userId),
                        followService.followingCount(jwt_myId),
                        followService.isFollowing(userId, jwt_myId)
                )
        );
    }

    /**
     * 내 피드 목록 가져오기 - 무한 스크롤 9개씩
     */
    @GetMapping("/myFeeds")
    public ResponseEntity<?> myFeeds(
            @PageableDefault(page = 0, size = 9, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        Long jwt_myId = 1L;

        return ResponseEntity.ok(
                ProfileFeedResponseDto.by(
                        feedService.findProfileFeedsByUserId(jwt_myId, pageable)
                )
        );
    }

    /**
     * 내가 보관한 피드 목록 가져오기 - 무한 스크롤 9개씩
     */
    @GetMapping("/savedFeeds")
    public ResponseEntity<?> savedFeeds(
            @PageableDefault(page = 0, size = 9, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        Long jwt_myId = 1L;

        return ResponseEntity.ok(
                ProfileFeedResponseDto.of(
                        profileService.findSavedFeeds(jwt_myId, pageable)
                )
        );
    }

    /**
     * 프로필 수정 페이지 보기
     */
    @GetMapping("/profile")
    public ResponseEntity<?> editProfile() {
        Long jwt_myId = 1L;

        return ResponseEntity.ok(
                ProfileEditResponseDto.from(userService.findById(jwt_myId))
        );
    }

    /**
     * 프로필 수정
     */
    @PutMapping()
    public ResponseEntity<?> editProfile(@RequestBody ProfileImgEditRequestDto requestDto) {
        Long jwt_myId = 1L;

        userService.save(jwt_myId, requestDto);
        return ResponseEntity.ok().build();
    }

    /**
     * 프로필 이미지 수정
     */
    @PutMapping("/img")
    public ResponseEntity<?> editProfileImg(@RequestBody String imgUrl) {
        Long jwt_myId = 1L;

        userService.saveImg(jwt_myId, imgUrl);
        return ResponseEntity.ok().build();
    }

}
