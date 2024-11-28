package com.green.greengram_ver2.user;

import com.green.greengram_ver2.common.model.ResultResponse;
import com.green.greengram_ver2.user.model.UserSignInReq;
import com.green.greengram_ver2.user.model.UserSignInRes;
import com.green.greengram_ver2.user.model.UserSignUpReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {
    private final UserService service;

    @PostMapping("sign-up")
    public ResultResponse<Integer> postSignUp (@RequestPart (required = false) MultipartFile pic
                            , @RequestPart UserSignUpReq p){
        int result = service.postSignUp(pic , p);
        return ResultResponse.<Integer>builder()
                .resultMessage("회원가입완료")
                .resultData(result)
                .build();
    }
    @PostMapping("sign-in")
    public ResultResponse<UserSignInRes> selUserForSignIn (@RequestBody UserSignInReq p) {
        UserSignInRes result = service.selUserForSignIn(p);
        return ResultResponse.<UserSignInRes>builder()
                .resultMessage(result.getMessage())
                .resultData(result)
                .build();
    }
}
