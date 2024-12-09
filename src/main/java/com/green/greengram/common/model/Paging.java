package com.green.greengram.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.greengram.common.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Paging {
    private final static int DEFAULT = 10;
    @Schema(example = "1")
    private int size;
    @Schema(example = "2")
    private int page;
    @JsonIgnore
    private int startIdx;

    public Paging(Integer size , Integer page){
        this.page = page==null || page<=0? 1 : page;
        this.size = (size == null || size <= 0) ? Constants.getDefault_page_size() : size;
        this.startIdx = ( this.page - 1 ) * this.size;
    }
    // setter 사용 -> 있는 값은 보존 + 없는 값만 setter
}
