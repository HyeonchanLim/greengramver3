package com.green.greengram_ver2.feed.comment;


import com.green.greengram_ver2.feed.comment.model.FeedCommentDelReq;
import com.green.greengram_ver2.feed.comment.model.FeedCommentPostReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeedCommentService {
    private final FeedCommentMapper mapper;

    public long insFeedComment (FeedCommentPostReq p){
        int result = mapper.insFeedComment(p);
        return p.getFeedCommentId();
    }
}
