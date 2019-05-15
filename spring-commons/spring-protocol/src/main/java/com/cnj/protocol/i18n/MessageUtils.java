package com.cnj.protocol.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;
/**
 * Create by cnj on 2019-3-9
 */
@Component
public class MessageUtils {

    @Autowired
    MessageSource messageSource;

    private static MessageUtils instance;

    @PostConstruct
    public void init(){
        instance = this;
    }

    /**
     * 国际化
     * @param messageCode
     * @return
     */
    public static String getMessage(String messageCode, Object[] params) {
        return instance.messageSource.getMessage(messageCode, params, LocaleContextHolder.getLocale());
    }

    /**
     * 国际化
     * @param messageCode
     * @return
     */
    public static String getMessage(String messageCode) {
        return MessageUtils.getMessage(messageCode,null);
    }


}
