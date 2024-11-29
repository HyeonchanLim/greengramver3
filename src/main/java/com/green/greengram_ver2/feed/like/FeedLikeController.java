package com.green.greengram_ver2.feed.like;

import com.green.greengram_ver2.common.model.ResultResponse;
import com.green.greengram_ver2.feed.like.model.FeedLikeReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.boot.sql.init.dependency.DatabaseInitializationDependencyConfigurer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("feed/like")
public class FeedLikeController {

    private final FeedLikeService Service;

    @GetMapping
    public ResultResponse<Integer> feedLikeToggle(@ParameterObject @ModelAttribute FeedLikeReq p){
        log.info("FeedLikeController > feedLikeToggle > p: {}" , p);
        int result = Service.feedLikeToggle(p);
        return ResultResponse.<Integer>builder()
                .resultMessage(result == 0? "좋아요 취소" : "좋아요 등록")
                .resultData(result)
                .build();
    }
}
