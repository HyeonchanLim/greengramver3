package com.green.greengram.user;

import com.green.greengram.common.MyFileUtils;
import com.green.greengram.user.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;
    private final MyFileUtils myFileUtils;

    public int postSignUp (MultipartFile pic , UserSignUpReq p){
        String savedPicName = (pic != null ? myFileUtils.makeRandomFileName(pic) : null);
        String hashedPassword = BCrypt.hashpw(p.getUpw(),BCrypt.gensalt());
        p.setPic(savedPicName);
        p.setUpw(hashedPassword);

        int result = mapper.insUser(p);
        if (pic == null){
            return result;
        }
        long userId = p.getUserId();
        String middlePath = String.format("user/%d",userId);
        String filePath = String.format("%s/%s",middlePath,savedPicName);
        myFileUtils.makeFolders(middlePath);
        try {
            myFileUtils.transferTo(pic , filePath);
            // 임시파일에 있는 pic 을 filepath (저장하고 싶은 경로) 위치로
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    public UserSignInRes selUserForSignIn (UserSignInReq p){
        UserSignInRes res = mapper.selUserByUid(p.getUid());

        if (res == null){
            res = new UserSignInRes();
            res.setMessage("아이디를 확인해 주세요");
            return res;
        } else if (!BCrypt.checkpw(p.getUpw() , res.getUpw())){
            res = new UserSignInRes();
            res.setMessage("비밀번호를 확인해 주세요");
            return res;
        }
        res.setMessage("로그인 성공~");
        return res;
    }

    public UserInfoGetRes getUserInfo (UserInfoGetReq p){
        return mapper.selUserInfo2(p);
    }

    public String patchUserPic(UserPicPatchReq p){
        // 1. 저장할 파일명 생성 -> 확장자는 오리지날 파일명과 일치하게 한다. uuid + getExt 사용
        String savedPicName = (p.getPic() != null ? myFileUtils.makeRandomFileName(p.getPic()) : null);
        // 2. 기존 파일 삭제 (1. 폴더를 지운다 2.select 해서 기존 파일명을 얻어온다. 3.기존 파일명을 FrontEnd 에서 받는다.)
        String folderpath = String.format("user/%d", p.getSignedUserId());
        myFileUtils.makeFolders(folderpath);

        //File currentPic = new File(); // pk값 통해서 경로 가져오기
        // 3. 원하는 위치에 저장할 파일명으로 파일을 이동한다 transferTo 사용
        String deletePath = String.format("%s/user/%d" ,myFileUtils.getUploadPath() ,  p.getSignedUserId());
        myFileUtils.deleteFolder(deletePath,false);


        // 4. db 에 튜플을 수정(update)한다.
        p.setPicName(savedPicName);
        int result = mapper.updUserPic(p); // 튜플값

        if (p.getPic() == null) {
            return null;
        }

        String filePath = String.format("user/%d/%s" , p.getSignedUserId(),savedPicName);
        try {
            myFileUtils.transferTo(p.getPic(),filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return savedPicName;
    }
}
