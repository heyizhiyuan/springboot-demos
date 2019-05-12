package com.cnj;

import com.cnj.resource.PropertiesUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.PropertyFilePropertySource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.PropertyResourceBundle;

/**
 * Create by cnj on 2019-5-12
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class Propertiestest {

    @Autowired
    PropertySourcesPlaceholderConfigurer propertyFilePropertySource;

    @Autowired
    Environment environment;

    @Test
    public void test1(){

        log.info("resources:{}",propertyFilePropertySource.getAppliedPropertySources().get("localProperties").getProperty("a"));
    }

    @Test
    public void test2(){
        log.info("val:{}",PropertiesUtils.getValue("a1"));
    }

    @Test
    public void test3(){
        log.info("val2:{}",PropertiesUtils.getEnvValue("env1"));
        log.info("val3:{}",environment.getProperty("env1"));
    }


}
