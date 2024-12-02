package com.green.greengram_ver2.feed.model;

import com.green.greengram_ver2.feed.comment.model.FeedCommentGetRes;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FeedGetRes {
    private long feedId;
    private long writerUserId;
    private String contents;
    private String location;
    private String createdAt;
    private String writerPic;
    private int isLike;
    private String writerNm;
    private FeedCommentGetRes comment;

    private List<String> pics = new ArrayList<>();
}
