package com.green.greengram_ver2.feed.comment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedCommentPostReq {
    @JsonIgnore
    private long feedCommentId;

    @Schema(title = "피드 PK" , example = "1" , requiredMode = Schema.RequiredMode.REQUIRED)
    private long feedId;
    @Schema(title = "로그인 유저 PK" , example = "2" , requiredMode = Schema.RequiredMode.REQUIRED)
    private long userId;
    @Schema(title = "댓글" , example = "안녕하세요" , requiredMode = Schema.RequiredMode.REQUIRED)
    private String comment;

}
