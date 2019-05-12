package com.cnj.resource;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * Create by cnj on 2019-5-12
 */
@Component
@Slf4j
public class PropertiesUtils {

    @Autowired
    PropertySourcesPlaceholderConfigurer propertySources;

    private static PropertiesUtils instance;

    @PostConstruct
    private void init(){
        instance = this;
    }

    /**
     *
     * @param key
     * @return
     */
    public static String getValue(String key){
        Object value = getValue("localProperties",key);
        return Objects.isNull(value)?null:String.valueOf(value);
    }

    /**
     *
     * @param key
     * @return
     */
    public static String getEnvValue(String key){
        Object value = getValue("environmentProperties",key);
        return Objects.isNull(value)?null:String.valueOf(value);
    }

    /**
     *
     * @param fileTypeName environmentProperties | localProperties
     * @param key
     * @return
     */
    public static Object getValue(String fileTypeName,String key){
        return  instance.propertySources
                .getAppliedPropertySources()
                .get(fileTypeName)
                .getProperty(key);
    }


}
