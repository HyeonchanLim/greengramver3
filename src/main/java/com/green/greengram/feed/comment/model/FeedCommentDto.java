package com.green.greengram.feed.comment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

//dto = data transfer object
@Getter
@Setter
public class FeedCommentDto {
    @JsonIgnore
    private long feedId;
    @Schema(title = "피드 댓글 PK")
    private long writerUserId;
    @Schema(title = "피드 댓글 내용")
    private long feedCommentId;
    @Schema(title = "피드 댓글 작성자 유저 PK")
    private String writerPic;
    @Schema(title = "피드 댓글 작성자 유저 이름")
    private String writerNm;
    @Schema(title = "피드 댓글 작성자 유저 프로필 사진 파일명")
    private String comment;
}
