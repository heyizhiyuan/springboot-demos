package com.cnj.resource;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Create by cnj on 2019-5-12
 */
@Configuration
@Slf4j
public class PropertiesConfig {

//    /**
//     *
//     * @param resource
//     * @return
//     */
//    @Bean
//    public PropertySourcesPlaceholderConfigurer createPropertySourcesPlaceholderConfigurer(@Value("config")ClassPathResource resource){
//        PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();;
//        List<Resource> resources = Lists.newArrayList();
//        try {
//            loadFileList(resource.getFile(),resources);
//            propertyPlaceholderConfigurer.setLocations(resources.stream().toArray((size)->new FileSystemResource[size]));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return propertyPlaceholderConfigurer;
//    }

    /**
     *
     *
     * @param root
     * @param resources
     */
    private void loadFileList(File root,List<Resource> resources){
        if (Objects.isNull(root) || !root.exists()){
            return;
        }
        root.listFiles((file)->{
            if (file.isDirectory()){
                this.loadFileList(file,resources);
                return true;
            }
            if (file.getName().endsWith(".properties")){
                resources.add(new FileSystemResource(file.getAbsolutePath()));
                return false;
            }
            return false;
        });
    }

}
