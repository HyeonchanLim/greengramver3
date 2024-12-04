package com.green.greengram_ver2.feed.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
/*
feed_pics 테이블에 튜플 여러개를 insert 한문장으로 처리하기 위해 사용하는 객체
 */
public class FeedPicDto {
    @Schema(title = "피드 ID")
    private long feedId;
    @Schema(title = "사진")
    private List<String> pics;
}

