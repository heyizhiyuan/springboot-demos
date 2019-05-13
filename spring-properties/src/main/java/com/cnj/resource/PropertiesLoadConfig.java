package com.cnj.resource;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Create by cnj on 2019-5-12
 */
@Configuration
@Slf4j
public class PropertiesLoadConfig {

    /**
     *
     * @param resource
     * @return
     */
    @Bean
    public PropertySourcesPlaceholderConfigurer createPropertySourcesPlaceholderConfigurer(@Value("config")ClassPathResource resource){
        PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();;
        try {
            List<String> paths = Lists.newArrayList();
            loadFileList(resource.getFile(),paths);
            propertyPlaceholderConfigurer.setLocations(paths.stream().map(FileSystemResource::new).toArray(FileSystemResource[]::new));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return propertyPlaceholderConfigurer;
    }

    /**
     *
     *
     * @param root
     * @param paths
     */
    private void loadFileList(File root,List<String> paths){
        if (Objects.isNull(root) || !root.exists()){
            return;
        }
        root.listFiles((file)->{
            if (file.isDirectory()){
                this.loadFileList(file,paths);
                return true;
            }
            if (file.getName().endsWith(".properties")){
                paths.add(file.getAbsolutePath());
            }
            return false;
        });
    }

}
