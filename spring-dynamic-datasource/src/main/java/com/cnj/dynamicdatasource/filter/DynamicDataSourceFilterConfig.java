package com.cnj.dynamicdatasource.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Create by cnj on 2019-5-9
 */
@Configuration
public class DynamicDataSourceFilterConfig {

    @Bean
    public FilterRegistrationBean registerKeywordHandleFilter () {
        FilterRegistrationBean filterBean = new FilterRegistrationBean();
        filterBean.setFilter(dynamicDatasourceFilter());
        filterBean.addUrlPatterns("/*");
        filterBean.addInitParameter(DynamicDatasourceFilter.EXCLUSION_KEY,"/login,/logout,/druid");
        return filterBean;
    }

    @Bean
    public DynamicDatasourceFilter dynamicDatasourceFilter(){
        return new DynamicDatasourceFilter();
    }

}
