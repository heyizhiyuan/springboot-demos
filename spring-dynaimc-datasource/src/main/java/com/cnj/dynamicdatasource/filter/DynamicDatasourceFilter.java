package com.cnj.dynamicdatasource.filter;

import com.cnj.dynamicdatasource.DynamicDataSourceContextHolder;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Create by cnj on 2019-5-9
 */
@Slf4j
public class DynamicDatasourceFilter implements Filter {

    public final static String EXCLUSION_KEY = "request_exclusions";

    private List<String> excludedUris;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.excludedUris = Arrays.asList(filterConfig.getInitParameter(EXCLUSION_KEY).split(","));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String uid;
        if (isExcludedUri(request.getServletPath())){
            filterChain.doFilter(request,response);
            return;
        }
        if (Strings.isNullOrEmpty(uid=request.getHeader(DynamicDataSourceContextHolder.DATA_SOURCE_KEY))){
            response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED,"缺少必要的请求头:"+DynamicDataSourceContextHolder.DATA_SOURCE_KEY);
            throw new RuntimeException("缺少必要的请求头:"+DynamicDataSourceContextHolder.DATA_SOURCE_KEY);
        }
        DynamicDataSourceContextHolder.setDataSourceKey(uid);
        filterChain.doFilter(request,response);
    }

    private boolean isExcludedUri(String uri) {
        if (Objects.isNull(this.excludedUris)||this.excludedUris.isEmpty()) {
            return false;
        }
        return this.excludedUris.stream().anyMatch((excludedUri)->uri.toLowerCase().matches(excludedUri.toLowerCase().replace("*",".*")));
    }

    @Override
    public void destroy() {
    }
}
