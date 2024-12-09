package com.green.greengram.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(title = "유저 정보 get 응답")
public class UserInfoGetRes {
    private long userId;
    private String pic;
    private String createdAt;
    private String nickName;
    private int follower;
    private int following;
    @Schema(title = "등록한 피드 카운트")
    private int feedCount;
    @Schema(title = "피드 좋아요 숫자", description = "사용자 피드에 달린 좋아요 카운트" , example = "2" , requiredMode = Schema.RequiredMode.REQUIRED)
    private int myFeedLikeCount;
    private int followState;
}
