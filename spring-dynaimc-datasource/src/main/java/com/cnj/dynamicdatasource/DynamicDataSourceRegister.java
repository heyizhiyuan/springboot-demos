package com.cnj.dynamicdatasource;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by cnj on 2019-4-16
 */
@Slf4j
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    //主数据源
    private DataSource baseDataSource;

    //从数据源列表
    private Map<String, DataSource> multiDataSourceMap;

    public final static String BASE_DATASOURCE_PREFIX = "spring.datasource";

    public final static String MULTI_DATASOURCE_PREFIX = "custom.datasource";

    public enum DataSourceProperty{
        url("url"),
        password("password"),
        driverClassName("driver-class-name"),
        username("username"),
        type("type");
        String value;
        DataSourceProperty(String value){
            this.value = value;
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        baseDataSource = loadBaseDataSource(environment);
        multiDataSourceMap = loadMultiSource(environment);
    }

    /**
     *
     * @param environment
     * @return
     */
    private Map<String, DataSource> loadMultiSource(Environment environment) {
        //1.获取所有数据源名称
        Map<String,String> sourceMap = this.getSourceMap(MULTI_DATASOURCE_PREFIX,environment);
        Map<String,DataSource> multiDataSource = Maps.newHashMap();
        sourceMap.keySet().stream().forEach((dataSourceId)->{
            multiDataSource.put(dataSourceId,buildDataSource(this.getSourceMap(MULTI_DATASOURCE_PREFIX.concat(".").concat(dataSourceId),environment)));
        });
        return multiDataSource;
    }

    private Map<String,String> getSourceMap(String prefix,Environment environment){
        return Binder.get(environment).bind(prefix,Map.class).get();
    }

    /**
     * 加载主数据源
     * @param environment
     */
    private DataSource loadBaseDataSource(Environment environment) {
        Map<String,String> baseConfigMap = Binder.get(environment).bind(BASE_DATASOURCE_PREFIX,Map.class).get();
        return buildDataSource(baseConfigMap);
    }

    /**
     * 创建数据源对象
     * @param dataSourceMap
     * @return
     */
    public DataSource buildDataSource(Map<String,String> dataSourceMap){
        return DataSourceBuilder
                .create()
                .username(dataSourceMap.get(DataSourceProperty.username.value))
                .password(String.valueOf(dataSourceMap.get(DataSourceProperty.password.value)))
                .url(dataSourceMap.get(DataSourceProperty.url.value))
                .driverClassName(dataSourceMap.get(DataSourceProperty.driverClassName.value))
                .type(getDataSourceType(dataSourceMap.get(DataSourceProperty.type.value)))
                .build();
    }

    /**
     *
     * @param clazz
     * @return
     */
    public Class<? extends DataSource> getDataSourceType(String clazz){
        try {
            return (Class<? extends DataSource>) Class.forName(clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        Map<Object, Object> targetDataSources = new HashMap();
        targetDataSources.put("dataSource", baseDataSource);
        targetDataSources.putAll(multiDataSourceMap);
        targetDataSources
                .keySet()
                .forEach((dataSourceId)->DynamicDataSourceContextHolder.addDataSourceId((String) dataSourceId));
        DynamicDataSourceContextHolder.setDataSourceKey("dataSource");
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);
        beanDefinition.setSynthetic(true);
        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        mpv.addPropertyValue("defaultTargetDataSource", baseDataSource);
        mpv.addPropertyValue("targetDataSources", targetDataSources);
        beanDefinitionRegistry.registerBeanDefinition("dataSource", beanDefinition);
    }


}




