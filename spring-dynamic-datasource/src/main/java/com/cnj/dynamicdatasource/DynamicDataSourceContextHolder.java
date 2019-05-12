package com.cnj.dynamicdatasource;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import java.util.*;

/**
 * Create by cnj on 2019-4-16
 */
@Slf4j
public class DynamicDataSourceContextHolder implements AutoCloseable {

    private final static ThreadLocal<String> DATASOURCE_LOCAL = new ThreadLocal<>();

    private final static Set<String> DATASOURCE_ID_SET = new HashSet<>();

    public final static String DATA_SOURCE_KEY = "uid";

    public static String getDataSourceKey() throws Exception{
        String key;
        if (Strings.isNullOrEmpty(key = DATASOURCE_LOCAL.get())){
            throw new RuntimeException("获取数据源失败");
        }
        return key;
    }

    public static void setDataSourceKey(String key){
        if (Objects.isNull(key)){
            log.warn("设置的数据源为空");
        }
        if (!isExists(key)){
            throw new RuntimeException(String.format("数据源:%s,不存在", key));
        }
        log.info("设置数据源:{} --> {}",key,DATASOURCE_LOCAL.get(),key);
        DATASOURCE_LOCAL.set(key);
    }

    /**
     *
     * @param dataSourceId
     */
    public static void addDataSourceId(String dataSourceId){
        if (Strings.isNullOrEmpty(dataSourceId)){
            throw new RuntimeException("数据库id不能为空");
        }
        if (isExists(dataSourceId)){
            log.warn("数据库id不能重复添加");
            return;
        }
        DATASOURCE_ID_SET.add(dataSourceId);
    }

    /**
     *
     * @return
     */
    public static Set<String> getDataSourceIdSet(){
        return DATASOURCE_ID_SET;
    }

    /**
     *
     * @param dataSourceId
     * @return
     */
    public static boolean isExists(String dataSourceId){
        return getDataSourceIdSet().contains(dataSourceId);
    }

    @Override
    public void close() throws Exception {
        DATASOURCE_LOCAL.remove();
    }
}