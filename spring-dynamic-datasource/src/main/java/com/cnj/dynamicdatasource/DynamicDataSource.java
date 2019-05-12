package com.cnj.dynamicdatasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
/**
 * Create by cnj on 2019-4-16
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    protected Object determineCurrentLookupKey() {
        try {
            log.debug("动态数据源切换为：{}",DynamicDataSourceContextHolder.getDataSourceKey());
            return DynamicDataSourceContextHolder.getDataSourceKey();
        } catch (Exception e) {
            log.error("切换数据源时出现异常:{}",e);
            return null;
        }
    }
}