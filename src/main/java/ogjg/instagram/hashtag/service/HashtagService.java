package ogjg.instagram.hashtag.service;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.hashtag.domain.Hashtag;
import ogjg.instagram.hashtag.repository.HashtagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HashtagService {

    private final HashtagRepository hashtagRepository;

//    @Transactional(readOnly = true)
//    public Hashtag findByHashtag(String hashtag) {
//        return hashtagRepository.findByContent(hashtag)
//                .orElseThrow(() -> new IllegalArgumentException("해당 해시태그가 존재하지 않습니다. hashtag=" + hashtag));
//    }

    @Transactional
    public List<Hashtag> saveAll(List<Hashtag> hashtags) {
        return hashtagRepository.saveAll(hashtags);
    }
}
