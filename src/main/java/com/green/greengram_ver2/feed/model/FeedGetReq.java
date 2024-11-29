package com.green.greengram_ver2.feed.model;

import com.green.greengram_ver2.common.model.Paging;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.BindParam;

import java.beans.ConstructorProperties;

@Getter
@Slf4j
@ToString(callSuper = true) // 부모한테 있는 값도 찍음 , 없으면 본인꺼만 찍음
public class FeedGetReq extends Paging {
    @Schema(title = "로그인 유저 pk" , name = "signed_user_id", example = "1" , requiredMode = Schema.RequiredMode.REQUIRED)
    private long signedUserId;

//    @ConstructorProperties({"page", "size", "signed_user_id"})
    public FeedGetReq (Integer page , Integer size ,@BindParam("signed_user_id") long signedUserId){
        super(page,size);
        this.signedUserId=signedUserId;
    }
}
