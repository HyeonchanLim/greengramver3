package com.green.greengram_ver2.feed.like;

import com.green.greengram_ver2.feed.like.model.FeedLikeReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedLikeService {
    private final FeedLikeMapper mapper;

    @PostMapping
    public Integer feedLikeToggle (FeedLikeReq p){
        int result = mapper.delFeedLike(p);
        if (result == 0){
            return mapper.insFeedLike(p); // 좋아요 등록이 되었을 때
        }
        return 0; // 좋아요 취소가 되었을 때
    }
}
