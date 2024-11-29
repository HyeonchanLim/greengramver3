package com.green.greengram_ver2.feed.like;

import com.green.greengram_ver2.feed.like.model.FeedLikeReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FeedLikeMapper {
    int insFeedLike (FeedLikeReq p);
    int delFeedLike (FeedLikeReq p);
}
