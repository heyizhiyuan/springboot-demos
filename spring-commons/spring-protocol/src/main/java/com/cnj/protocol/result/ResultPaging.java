package com.cnj.protocol.result;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * Create by cnj on 2019-5-15
 */

@Getter
@ToString
public class ResultPaging<T> {

    ResultPage page;

    List<T> list;

    ResultPaging(List<T> list,ResultPage page){
        this.page = page;
        this.list = list;
    }

    public static <T> ResultPaging<T> paging(List<T> list,long totalCount,int pageNo,int pageSize){
        return new ResultPaging(list,ResultPage.page(totalCount,pageNo,pageSize));
    }

    public static <T> ResultPaging<T> paging(List<T> list,long totalCount,int pageNo){
        return new ResultPaging(list,ResultPage.page(totalCount,pageNo));
    }
}
