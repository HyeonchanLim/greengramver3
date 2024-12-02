package com.green.greengram_ver2.feed.comment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedCommentDelReq {
    @JsonIgnore
    private long feedCommentId;


}
