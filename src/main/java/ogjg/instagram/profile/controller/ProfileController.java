package ogjg.instagram.profile.controller;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.feed.service.FeedService;
import ogjg.instagram.profile.dto.request.ProfileEditRequestDto;
import ogjg.instagram.profile.dto.request.ProfileImgEditRequestDto;
import ogjg.instagram.profile.dto.response.ProfileEditResponseDto;
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
    private final FeedService feedService;
    private final ProfileService profileService;

    /**
     * 프로필 가져오기
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> profile(@PathVariable("userId") Long userId) {
        Long jwt_myId = 1L;

        return ResponseEntity.ok(
                profileService.findProfile(userId, jwt_myId));
    }

    /**
     * 내 피드 목록 가져오기 - 무한 스크롤 9개씩
     */
    @GetMapping("/{userId}/feeds")
    public ResponseEntity<?> myFeeds(
            @PathVariable ("userId") Long userId,
            @PageableDefault(page = 0, size = 9, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        Long jwt_myId = 1L;

        return ResponseEntity.ok(
                feedService.findProfileFeedsByUserId(userId, pageable));
    }

    /**
     * 내가 보관한 피드 목록 가져오기 - 무한 스크롤 9개씩
     */
    @GetMapping("/collected-feeds")
    public ResponseEntity<?> collectedFeeds(
            @PageableDefault(page = 0, size = 9, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        Long jwt_myId = 1L;

        return ResponseEntity.ok(
                profileService.findCollectedFeeds(jwt_myId, pageable));
    }

    /**
     * 프로필 수정 페이지 보기
     */
    @GetMapping("/profile")
    public ResponseEntity<?> editProfile() {
        Long jwt_myId = 1L;

        return ResponseEntity.ok(
                ProfileEditResponseDto.from(userService.findById(jwt_myId)));
    }

    /**
     * 프로필 수정
     */
    @PutMapping("")
    public ResponseEntity<?> editProfile(@RequestBody ProfileEditRequestDto requestDto) {
        Long jwt_myId = 1L;

        userService.save(jwt_myId, requestDto);
        return ResponseEntity.ok().build();
    }

    /**
     * 프로필 이미지 수정
     */
    @PutMapping("/img")
    public ResponseEntity<?> editProfileImg(@RequestBody ProfileImgEditRequestDto requestDto) {
        Long jwt_myId = 1L;

        userService.saveImg(jwt_myId, requestDto);
        return ResponseEntity.ok().build();
    }

}
