package com.green.greengram.feed.comment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.greengram.common.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

import java.beans.ConstructorProperties;

@Getter
@ToString
@Schema(title = "피드 댓글 리스트 요청")
public class FeedCommentGetReq {
    private final static int FIRST_COMMENT_SIZE = 3;
//    private final static int DEFAULT_PAGE_SIZE = 20;
//    yaml 20 작성했으니 제외

    @Schema(title = "피드 pk", name = "feed_id", description = "피드 PK", example = "1" , requiredMode = Schema.RequiredMode.REQUIRED)
    private long feedId; // n + 1 방식이라 feedId 가 필요함
    @Schema(title="ㅅ", description = "피드 PK", name="start_idx", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private int startIdx;
    @JsonIgnore
    private int size;


    @ConstructorProperties({"feed_id", "start_idx", "size"})
    public FeedCommentGetReq(long feedId, int startIdx, Integer size) {
        // bindparam - 클라이언트인 url 주소랑 맞춤 , 즉 url 키값은 camel 케이스 기법이 아닌
        // feed_id 인 _ 를 사용하는 값을 쓰고 있음
        // 그리고 url 에서 넘어오는 값을 받음
        this.feedId = feedId;
        this.startIdx = startIdx;
        this.size = (size == null ? Constants.getDefault_page_size() : size) + 1;
    }
}
