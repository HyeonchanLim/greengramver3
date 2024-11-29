package com.green.greengram_ver2.feed;

import com.green.greengram_ver2.common.model.ResultResponse;
import com.green.greengram_ver2.feed.model.FeedGetReq;
import com.green.greengram_ver2.feed.model.FeedGetRes;
import com.green.greengram_ver2.feed.model.FeedPostReq;
import com.green.greengram_ver2.feed.model.FeedPostRes;
import com.green.greengram_ver2.user.model.UserSignInReq;
import com.green.greengram_ver2.user.model.UserSignInRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("feed")
@Tag(name = "2. 피드" , description = "피드 관리")
public class FeedController {
    private final FeedService service;

    @PostMapping
    @Operation
    public ResultResponse<FeedPostRes> postFeed (@RequestPart List<MultipartFile> pics
                                                , @RequestPart FeedPostReq p) {
        FeedPostRes res = service.postFeed(pics , p);
        return ResultResponse.<FeedPostRes>builder()
                .resultMessage("피드 등록 완료")
                .resultData(res)
                .build();
    }
    @GetMapping
    @Operation(summary = "Feed 리스트", description = "loginUserId는 로그인한 사용자의 pk")
    public ResultResponse<List<FeedGetRes>> getFeedList(@ParameterObject @ModelAttribute FeedGetReq p) {
        log.info("FeedController > getFeedList > p: {}", p);
        //List<FeedGetRes> list = service.getFeedList(p);
        List<FeedGetRes> list = service.selFeedList(p);
        log.info("FeedController > getFeedList > aaaaaaaaa: {}", list);
        return ResultResponse.<List<FeedGetRes>>builder()
                .resultMessage(String.format("%d rows", list.size()))
                .resultData(list)
                .build();
    }

}
