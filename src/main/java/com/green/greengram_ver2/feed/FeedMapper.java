package com.green.greengram_ver2.feed;

import com.green.greengram_ver2.feed.model.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FeedMapper {
    int insFeed (FeedPostReq p);
}
