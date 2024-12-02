package com.green.greengram_ver2.feed.comment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.greengram_ver2.common.model.Paging;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FeedCommentGetRes {
    private boolean moreComment;
    private List<FeedCommentDto> commentList;

}
