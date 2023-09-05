package ogjg.instagram.hashtag.service;

import lombok.RequiredArgsConstructor;
import ogjg.instagram.hashtag.domain.Hashtag;
import ogjg.instagram.hashtag.repository.HashtagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HashtagService {

    private final HashtagRepository hashtagRepository;

    @Transactional
    public List<Hashtag> saveAll(List<Hashtag> hashtags) {
        return hashtagRepository.saveAll(hashtags);
    }
}
