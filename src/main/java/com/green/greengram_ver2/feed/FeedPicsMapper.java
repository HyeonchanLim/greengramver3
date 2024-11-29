package com.green.greengram_ver2.feed;

import com.green.greengram_ver2.feed.model.FeedPicDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedPicsMapper {
    int insFeedPics (FeedPicDto p);
    int insFeedPics2(FeedPicDto p);
    List<String> selFeedPics(long feedId);
}
