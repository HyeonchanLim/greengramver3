package com.green.greengram.feed;

import com.green.greengram.common.MyFileUtils;
import com.green.greengram.feed.comment.FeedCommentMapper;
import com.green.greengram.feed.comment.model.FeedCommentDto;
import com.green.greengram.feed.comment.model.FeedCommentGetReq;
import com.green.greengram.feed.comment.model.FeedCommentGetRes;
import com.green.greengram.feed.model.*;
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
    private final FeedpicMapper feedpicMapper;
    private final MyFileUtils myFileUtils;
    private final FeedCommentMapper feedCommentMapper;

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

        // 랜덤 파일명 저장용 >> feed_pic 테이블에 저장할 때 사용
        // pic.size() 작성한 이유 -> 근사치 설정하기 위해서 why? 속도 향상
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
            // 피드 하나당 사진이 여러개 -> 1 : n 관계  - for 문으로 반복
        }
        FeedPicDto feedPicDto = new FeedPicDto();
        feedPicDto.setPics(picNameList);
        feedPicDto.setFeedId(feedId);
        int resultpics = feedpicMapper.insFeedpic(feedPicDto);

        // postres SETTER 사용했을 경우
//        FeedPostRes res = new FeedPostRes();
//        res.setFeedId(feedId);
//        res.setpic(picNameList);

        return FeedPostRes.builder()
                .feedId(feedId)
                .pics(picNameList)
                .build();
    }

    public List<FeedGetRes> selFeedList (FeedGetReq p){
        // n + 1 이슈 발생 -> 리스트를 만들었기 때문에 리스트 가져오는데 +1회가 발생함
        List<FeedGetRes> list = mapper.selFeedList(p);
        //피드 당 사진 , item - feedid 각각의 튜플들 담아서 사용
        // 1번 feedid 작업 끝나면 다음 feedid 넘어가면서 계속 진행(for문 반복)
        for (FeedGetRes item : list){
            // 피드 당 사진 리스트
            item.setPics(feedpicMapper.selFeedPicList(item.getFeedId()));

            // 피드 당 댓글 4개
            FeedCommentGetReq commentGetReq = new FeedCommentGetReq(item.getFeedId(), 0, 3);

            List<FeedCommentDto> commentList = feedCommentMapper.selFeedCommentList(commentGetReq);
            // commentdto 타입 & xml에서 설정한 튜플들로 목록 설정 ->
            // 매개변수 자체가 튜플이라 생각
            // 튜플에 대응하는 컬럼을 작성 -> 2개 이상이라면 list
            FeedCommentGetRes commentGetRes = new FeedCommentGetRes();
            commentGetRes.setCommentList(commentList);
            commentGetRes.setMoreComment(commentList.size() == commentGetReq.getSize());

            if (commentGetRes.isMoreComment()){
                commentList.remove(commentList.size()-1);
            }
            item.setComment(commentGetRes);
            // 코멘트 값 설정 1 or 0 , 4개면 true , 아니면 false

            // 댓글 0 페이지에서 시작 부분은 0,4 -> 만약 댓글을 삭제하면 3으로 바뀌는데 더 보기 누를 경우 3, 20
            // 1개 더 삭제하면 2, 20 으로 시작
        }
        return list;
    }
    @Transactional
    public int deleteFeed(FeedDeleteReq p) {
        //피드 사진 삭제
        String deletePath = String.format("%s/feed/%d", myFileUtils.getUploadPath(), p.getFeedId());
        myFileUtils.deleteFolder(deletePath, true);

        //피드 댓글, 좋아요 삭제
        int affectedRows = mapper.delFeedLikeAndFeedCommentAndFeedPic(p);
        log.info("affectedRows: {}", affectedRows);

        //피드 삭제
        return mapper.delFeed(p);
    }
}
