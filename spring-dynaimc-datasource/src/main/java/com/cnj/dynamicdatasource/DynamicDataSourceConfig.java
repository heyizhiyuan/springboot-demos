package com.cnj.dynamicdatasource;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Create by cnj on 2019-5-8
 */
@Configuration
@Import(DynamicDataSourceRegister.class)
public class DynamicDataSourceConfig {
}
