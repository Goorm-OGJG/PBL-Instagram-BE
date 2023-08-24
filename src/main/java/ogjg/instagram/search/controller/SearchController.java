package ogjg.instagram.search.controller;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.config.security.jwt.JwtUserDetails;
import ogjg.instagram.search.service.SearchService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/search", produces = MediaType.APPLICATION_JSON_VALUE)
public class SearchController {

    private final SearchService searchService;

    @GetMapping("")
    public ResponseEntity<?> search(
            @RequestParam("search") String search,
            @RequestParam("type") String type,
            @PageableDefault(page = 0, size = 10) Pageable pageable,
            @AuthenticationPrincipal JwtUserDetails userDetails
            ) {

        Long loginId = userDetails.getUserId();
        boolean isUser;

        // todo : type enum, factory 고려
        if (type.trim().equals("hashtag")) {
            return ResponseEntity.ok(searchService.searchByHashtag(isUser = false, search, pageable));

        } else if (type.trim().equals("user")) {
            return ResponseEntity.ok(searchService.searchByNickname(isUser = true, loginId, search, pageable));

        } else {
            throw new IllegalArgumentException("잘못된 검색 타입입니다.");
        }
    }

    /**
     * 태그 정보 및 태그 걸린 피드 가져오기
     */
    @GetMapping("{hashtag}/feed")
    public ResponseEntity<?> searchHashTag(@PathVariable("hashtag") String content,
            @AuthenticationPrincipal JwtUserDetails userDetails
    ) {
        Long loginId = userDetails.getUserId();

        Sort sortByCreatedTime = Sort.by(Sort.Order.desc("createdAt"));
        PageRequest pageRequest = PageRequest.of(0, 9, sortByCreatedTime);

        return ResponseEntity.ok(
                searchService.searchResult(content, pageRequest)
        );
    }

}
