package com.green.greengram_ver2.feed;

import com.green.greengram_ver2.common.MyFileUtils;
import com.green.greengram_ver2.common.model.ResultResponse;
import com.green.greengram_ver2.feed.model.FeedPicDto;
import com.green.greengram_ver2.feed.model.FeedPostReq;
import com.green.greengram_ver2.feed.model.FeedPostRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeedService {
    private final FeedMapper mapper;
    private final FeedPicsMapper feedPicsMapper;
    private final MyFileUtils myFileUtils;

    public FeedPostRes postFeed(List<MultipartFile> pics , FeedPostReq p){
        int result = mapper.insFeed(p);
        long feedId = p.getFeedId();
        String middlePath = String.format("feed/%d",feedId);
        myFileUtils.makeFolders(middlePath);
        FeedPicDto feedPicDto = new FeedPicDto();
        feedPicDto.setFeedId(p.getFeedId());
        List<String> picsStr = new ArrayList<String>();
        for (MultipartFile pic : pics){
            String savedPicName = myFileUtils.makeRandomFileName(pic);
            String filePath = String.format("%s/%s",middlePath,savedPicName);
            try {
                myFileUtils.transferTo(pic , filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
