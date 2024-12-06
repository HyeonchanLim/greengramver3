package com.green.greengram.user.follow;

import com.green.greengram.common.model.ResultResponse;
import com.green.greengram.user.follow.model.UserFollowReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("user/follow")
public class UserFollowController {
    private final UserFollowService service;

    // 팔로우 신청
    // @RequestBody 요청을 보내는 자가 body 에 json 형태의 데이터를 담아서 보낸다.
    @PostMapping
    public ResultResponse<Integer> postUserFollow(@RequestBody UserFollowReq p){
        int result = service.postUserFollow(p);
        log.info("userFollowController > postUserFollow > p:{}" , p);
        return ResultResponse.<Integer>builder()
                .resultMessage("팔로우 완료")
                .resultData(result)
                .build();
    }
    // 팔로우 취소
    // 요청을 보내는 자가 데이터를 어떻게 보내나 ? 쿼리스트링
    @DeleteMapping
    public ResultResponse<Integer> deleteUserFollow(@ParameterObject @ModelAttribute UserFollowReq p){
        int result = service.deleteUserFollow(p);
        log.info("userFollowController > deleteUserFollow > p:{}" , p);
        return ResultResponse.<Integer>builder()
                .resultMessage("팔로우 취소")
                .resultData(result)
                .build();
    }

}
