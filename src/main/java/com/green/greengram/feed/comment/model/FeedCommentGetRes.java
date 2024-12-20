package com.green.greengram.feed.comment.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Getter
@Setter
@ToString
public class FeedCommentGetRes {
    private boolean moreComment;
    private List<FeedCommentDto> commentList;

}
