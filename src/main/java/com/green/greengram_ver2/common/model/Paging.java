package com.green.greengram_ver2.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Paging {
    private final static int DEFAULT = 10;
    private int size;
    private int page;
    @JsonIgnore
    private int startIdx;

    public Paging(Integer size , Integer page){
        this.page = page==null || page<=0? 1 : page;
        this.size = size==null || size<=0? DEFAULT : size;
        this.startIdx = (this.page-1)*this.size;
    }
    // setter 사용 -> 있는 값은 보존 + 없는 값만 setter
}
