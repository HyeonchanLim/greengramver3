package com.green.greengram_ver2.feed.comment.model;

import lombok.Getter;
import lombok.Setter;

//dto = data transfer object
@Getter
@Setter
public class FeedCommentDto {
    private long writerUserId;
    private long feedCommentId;
    private String writerPic;
    private String writerNm;
    private String comment;
}
