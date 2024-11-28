package com.green.greengram_ver2.user;

import com.green.greengram_ver2.user.model.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
   int insUser (UserSignUpReq p);
   UserSignInRes selUserByUid (String uid);
   // UserSignInReq -> uid 를 받아오기 때문에 String uid 로 써도 괜찮음

}
