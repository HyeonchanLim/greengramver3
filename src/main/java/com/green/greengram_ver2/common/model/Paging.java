package com.green.greengram_ver2.common.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Paging {
    private final static int DEFAULT = 10;
    private int size;
    private int page;
    private int startIdx;

    public Paging(Integer size , Integer page){
        this.page = page==null || page<=0? 1 : page;
        this.size = size==null || size<=0? DEFAULT : size;
        this.startIdx = (this.page-1)*this.size;
    }
}
