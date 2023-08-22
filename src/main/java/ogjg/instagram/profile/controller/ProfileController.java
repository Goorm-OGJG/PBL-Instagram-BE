package ogjg.instagram.profile.controller;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.config.security.jwt.JwtUserDetails;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @GetMapping("/{nickname}")
    public ResponseEntity<?> profile(
            @PathVariable("nickname") String nickname,
            @AuthenticationPrincipal JwtUserDetails userDetails
    ) {
        Long loginId = userDetails.getUserId();

        return ResponseEntity.ok(
                profileService.findProfile(nickname, loginId));
    }

    /**
     * 내 피드 목록 가져오기 - 무한 스크롤 9개씩
     */
    @GetMapping("/{nickname}/feeds")
    public ResponseEntity<?> myFeeds(
            @PathVariable ("nickname") String nickname,
            @PageableDefault(page = 0, size = 9, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable,
            @AuthenticationPrincipal JwtUserDetails userDetails
    ) {
        Long loginId = userDetails.getUserId();

        return ResponseEntity.ok(
                feedService.findProfileFeedsByUserId(nickname, pageable));
    }

    /**
     * 내가 보관한 피드 목록 가져오기 - 무한 스크롤 9개씩
     */
    @GetMapping("/collected-feeds")
    public ResponseEntity<?> collectedFeeds(
            @PageableDefault(page = 0, size = 9, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable,
            @AuthenticationPrincipal JwtUserDetails userDetails
    ) {
        Long loginId = userDetails.getUserId();

        return ResponseEntity.ok(
                profileService.findCollectedFeeds(loginId, pageable));
    }

    /**
     * 프로필 수정 페이지 보기
     */
    @GetMapping("/profile")
    public ResponseEntity<?> editProfile(
            @AuthenticationPrincipal JwtUserDetails userDetails
    ) {
        Long loginId = userDetails.getUserId();

        return ResponseEntity.ok(
                ProfileEditResponseDto.from(userService.findById(loginId)));
    }

    /**
     * 프로필 수정
     */
    @PutMapping("")
    public ResponseEntity<?> editProfile(@RequestBody ProfileEditRequestDto requestDto,
            @AuthenticationPrincipal JwtUserDetails userDetails) {
        Long loginId = userDetails.getUserId();

        userService.save(loginId, requestDto);
        return ResponseEntity.ok().build();
    }

    /**
     * 프로필 이미지 수정
     */
    @PutMapping("/img")
    public ResponseEntity<?> editProfileImg(@RequestBody ProfileImgEditRequestDto requestDto,
            @AuthenticationPrincipal JwtUserDetails userDetails) {
        Long loginId = userDetails.getUserId();

        userService.saveImg(loginId, requestDto);
        return ResponseEntity.ok().build();
    }

}
