package com.cnj.protocol.result;

import lombok.Getter;
import lombok.ToString;

/**
 * Create by cnj on 2019-5-15
 */
@Getter
@ToString
public class ResultPage {

    private final static int DEFAULT_PAGE_SIZE = 10;

    private long totalCount;

    private int pageNo;

    private int pageSize;

    private int totalPage;

    private ResultPage(){

    }

    public static ResultPage page(long totalCount,int pageNo,int pageSize){
        return new ResultPage(totalCount,pageNo,pageSize);
    }

    public static ResultPage page(long totalCount,int pageNo){
        return new ResultPage(totalCount,pageNo);
    }

    public ResultPage(long totalCount,int pageNo,int pageSize){
        if (totalCount>=0){
            this.totalCount = totalCount;
        }
        if (pageNo>0){
            this.pageNo = pageNo;
        }
        if (pageSize>0){
            this.pageSize = pageSize;
        }
        this.totalPage = (int)(this.totalCount + this.pageSize -1) /this.pageSize;
    }

    public ResultPage(long totalCount,int pageNo){
        this(totalCount,pageNo,DEFAULT_PAGE_SIZE);
    }

}
