package com.green.greengram.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.lang.reflect.Field;
import java.util.UUID;

@Slf4j
@Component //빈등록으로 @Value 사용 가능
public class MyFileUtils {
    private final String uploadPath;

    public String getUploadPath() {
        return uploadPath;
    }

    public MyFileUtils(@Value("${file.directory}") String uploadPath) {
        log.info("MyFileUtils - 생성자: {}", uploadPath);
        this.uploadPath = uploadPath;
    }

    public void makeFolders(String path) {
        File file = new File(uploadPath, path);
        // file ( " " ) 한번에
        // file ( "" , "" ) 1+1 = 2 ,는 / 로 변경되서 합쳐짐
        // 확장자 없어서 디렉토리일 확률이 엄청 높음
        if(!file.exists()) {
            file.mkdirs();
        }
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
        String originalFileName = file.getOriginalFilename();
        return makeRandomFileName(originalFileName);
    }

    public void transferTo(MultipartFile mf, String path) throws IOException {
        File file = new File(uploadPath, path);
        mf.transferTo(file);
    }
    // 폴더 삭제 , e.g."user/1"
    // 폴더삭제 x -> 폴더 안에 내용물만 삭제
    public void deleteFolder(String path , boolean deleteRootFolder){
        File folder = new File(path);
        //file.delete(); // 폴더 아래 파일 , 폴더가 없어야 지울 수 있음 그래서 파일 삭제 필요
        if(folder.exists() && folder.isDirectory()){ // 폴더가 존재하면서 디렉토리인가? 확인
            File[] includeFiles = folder.listFiles(); // 존재하는거만 넘겨줌
            // 파일인지 디렉토리인지 확인 절차 필요 why ? 파일 안에 내용물 있으면 삭제 불가능하기 때문
            // 디렉토리에 파일이 있는지 체크 -> 있으면 파일 제거하고 디렉토리 제거 , 없으면 디렉토리 제거
            // 재귀호출과 같음
            for (File f : includeFiles){
                if (f.isDirectory()){
                    deleteFolder(f.getAbsolutePath() , true);
                } else {
                    f.delete();
                }
            }
            // true 일 경우에만 폴더 삭제
            if (deleteRootFolder){
                folder.delete();
            }
        }
    }
}
