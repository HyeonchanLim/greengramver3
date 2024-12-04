package com.green.greengram_ver2.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Component
@Configuration // WebMvcConfigurer 이걸 통해서 bean 되는데 없다면 메소드에 @Bean 을 써서 bean 등록 해줘야함
// 메소드에서 bean 사용 -> 리턴 받은걸 싱글톤으로 만들어줌
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final String uploadPath;
    // final 에 값 넣을려면 생성자 or 명시적 ( = ?? ) 으로 값 입력

    public WebMvcConfiguration(@Value("${file.directory}") String uploadPath) {
        this.uploadPath = uploadPath;
    }

//    @Bean
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/pic/**")
                .addResourceLocations("file:" + uploadPath + "/");
    }
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer){
        configurer.addPathPrefix("api", HandlerTypePredicate.forAnnotation(RestController.class));
    }
}