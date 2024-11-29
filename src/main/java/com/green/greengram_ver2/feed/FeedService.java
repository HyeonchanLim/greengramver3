package com.green.greengram_ver2.feed;

import com.green.greengram_ver2.common.MyFileUtils;
import com.green.greengram_ver2.common.model.ResultResponse;
import com.green.greengram_ver2.feed.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    // 애노테이션 걸어두면 구현부 중간에 오류가 발생하면 아예 실행 X
    // 애노테이션 없으면 오류 직전까지의 실행부분이 실행 적용
    // 아래의 메소드에 구현부 내용이 많다 -> 트랜젝션
    public FeedPostRes postFeed(List<MultipartFile> pics , FeedPostReq p){
        int result = mapper.insFeed(p);
        // 파일 등록
        long feedId = p.getFeedId();

        //저장 폴더 만들기 , 저장위치/feed/${feedId}/파일들을 저장한다.
        String middlePath = String.format("feed/%d",feedId);
        myFileUtils.makeFolders(middlePath);

        // 랜덤 파일명 저장용 >> feed_pics 테이블에 저장할 때 사용
        // pics.size() 작성한 이유 -> 근사치 설정하기 위해서 why? 속도 향상
        List<String> picNameList = new ArrayList<>(pics.size());

        for (MultipartFile pic : pics){
            String savedPicName = myFileUtils.makeRandomFileName(pic);
            picNameList.add(savedPicName);
            String filePath = String.format("%s/%s",middlePath,savedPicName);
            try {
                myFileUtils.transferTo(pic , filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FeedPicDto feedPicDto = new FeedPicDto();
        feedPicDto.setPics(picNameList);
        feedPicDto.setFeedId(p.getFeedId());
        int resultPics = feedPicsMapper.insFeedPics(feedPicDto);

        // postres SETTER 사용했을 경우
//        FeedPostRes res = new FeedPostRes();
//        res.setFeedId(feedId);
//        res.setPics(picNameList);

        return FeedPostRes.builder()
                .feedId(feedId)
                .pics(picNameList)
                .build();
    }

    List<FeedGetRes> selFeedList (FeedGetReq p){
        List<FeedGetRes> list = mapper.selFeedList(p);

        return list;
    }
}
