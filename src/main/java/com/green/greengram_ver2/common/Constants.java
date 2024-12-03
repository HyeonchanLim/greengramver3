package com.green.greengram_ver2.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component // 빈 등록
public class Constants {
    private static int default_page_size;
    @Value("${const.default-page-size}")
    // value 안에 작성한 주소가 yaml 에 작성한 데이터 20 이
    // 밑에 int value 파라미터로 입력됨
    public void setDefault_page_size(int value) {
        default_page_size = value;
        // 파라미터로 받은 yaml 의 20 이 멤버필드 default_page_size 에 입력
    }
    public static int getDefault_page_size() {
        return default_page_size; // 객체
    }
}
