package com.cnj.protocol.util;

import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Create by cnj on 2019-3-7
 */
public class HttpRequestUtil {

    public static boolean isJsonRequest(HttpServletRequest httpRequest){

        return Objects.nonNull(httpRequest)
                && Objects.nonNull(httpRequest.getContentType())
                && httpRequest.getContentType().contains(MediaType.APPLICATION_JSON_VALUE);
    }


}
