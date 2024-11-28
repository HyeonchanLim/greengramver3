package com.green.greengram_ver2.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Locale;

@Getter
@Setter
@ToString
public class UserSignUpReq {
    @JsonIgnore
    private long userId;
    @JsonIgnore
    @Schema(title = "회원 사진")
    private String pic;

    @Schema(title = "회원 id" , example = "mic" , requiredMode = Schema.RequiredMode.REQUIRED)
    private String uid;
    @Schema(title = "회원 pw", example = "1212" , requiredMode = Schema.RequiredMode.REQUIRED)
    private String upw;
    @Schema(title = "회원 nickname")
    private String nickName;
}
