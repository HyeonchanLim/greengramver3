package com.green.greengram_ver2.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.util.UUID;

@Slf4j
@Component //빈등록으로 @Value 사용 가능
public class MyFileUtils {


    private final String uploadPath;

    public MyFileUtils(@Value("${file.directory}") String uploadPath) {
        log.info("MyFileUtils - 생성자: {}", uploadPath);
        this.uploadPath = uploadPath;
    }

    public String makeFolders(String path) {
        File file = new File(uploadPath, path);
        // file ( " " ) 한번에
        // file ( "" , "" ) 1+1 = 2 ,는 / 로 변경되서 합쳐짐
        // 확장자 없어서 디렉토리일 확률이 엄청 높음
        if(!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }
    public String getExt(String fileName) {
        int lastIdx = fileName.lastIndexOf(".");
        return fileName.substring(lastIdx);
    }
    public String makeRandomFileName() {
        return UUID.randomUUID().toString();
    }
    public String makeRandomFileName(String originalFileName) {
        return makeRandomFileName() + getExt(originalFileName);
    }
    public String makeRandomFileName(MultipartFile file) {
        return makeRandomFileName(file.getOriginalFilename());
    }
    public void transferTo(MultipartFile mf, String path) throws IOException {
        File file = new File(uploadPath, path);
        mf.transferTo(file);
    }
}
class Test {
    public static void main(String[] args) {
        MyFileUtils myFileUtils = new MyFileUtils("C:/temp");
        String randomFileName = myFileUtils.makeRandomFileName("sdvkljsdfajkldsfjkldsfljk.png");
        System.out.println(randomFileName);
    }
}
