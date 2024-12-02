package com.green.greengram_ver2.feed.comment;

import com.green.greengram_ver2.common.model.ResultResponse;
import com.green.greengram_ver2.feed.comment.model.FeedCommentPostReq;
import com.green.greengram_ver2.feed.like.model.FeedLikeReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("feed/comment")
public class FeedCommentController {
    private final FeedCommentService service;

    // commentid 는 bigint 라서 long 으로 받아와야하고 mapper service 에서는
    // 영향받은 행 값 이라서 int 쓰면 됨
    @PostMapping
    public ResultResponse<Long> insFeedComment (@RequestBody FeedCommentPostReq p){
        long result = service.insFeedComment(p);
        log.info("id {} , pk {}" , result , p);
        return ResultResponse.<Long>builder()
                .resultMessage("댓글 입력 완료")
                .resultData(result)
                .build();
    }
}
