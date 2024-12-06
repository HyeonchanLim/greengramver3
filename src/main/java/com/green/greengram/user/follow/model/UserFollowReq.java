package com.green.greengram.user.follow.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
public class UserFollowReq {
    // jsonproperty -> json 직렬화/역직렬화를 위해서 사용
    // schema -> swagger 용으로 사용
    @JsonProperty("from_user_id") // swagger 에서 camel 기법 + snake(aa_aa) 기법 전부 가능
    @Schema(name = "from_user_id" , description = "팔로워 유저 id", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private long fromUserId;
    @JsonProperty("to_user_id")
    @Schema(name = "to_user_id" , description = "팔로잉 유저 id", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private long toUserId;

    @ConstructorProperties({"from_user_id" , "to_user_id"})
    public UserFollowReq(long fromUserId, long toUserId) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }
}
