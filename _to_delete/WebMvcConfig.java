package com.seunghyeon.verysimplesns.config;

import com.seunghyeon.verysimplesns.interceptor.RateLimitInterceptor;
import com.seunghyeon.verysimplesns.ratelimit.ApiAction;
import com.seunghyeon.verysimplesns.ratelimit.RateLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final RateLimiter rateLimiter;
    private final String uploadDir;

    public WebMvcConfig(RateLimiter rateLimiter, @Value("${file.upload-dir}") String uploadDir) {
        this.rateLimiter = rateLimiter;
        this.uploadDir = uploadDir;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RateLimitInterceptor(rateLimiter, ApiAction.FEED_CREATE))
                .addPathPatterns("/feed/create");

        registry.addInterceptor(new RateLimitInterceptor(rateLimiter, ApiAction.FEED_FIND))
                .addPathPatterns("/feed/find");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + uploadDir + "/");
    }
}
