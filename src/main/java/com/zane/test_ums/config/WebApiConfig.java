package com.zane.test_ums.config;

import com.zane.test_ums.web.AuthorInterceptor;
import com.zane.test_ums.web.LogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 解决跨域问题：所有请求都允许跨域，使用这种配置方法就不能在 filter中再配置header
 * interceptor: 拦截器实例
 * path: 设置拦截器的过滤路径规则
 * excludePath: 设置不需要拦截的过滤规则
 * @author Zanezeng
 */
@Configuration
public class WebApiConfig implements WebMvcConfigurer {

    private final AuthorInterceptor authorInterceptor;
    private final LogInterceptor logInterceptor;

    @Autowired
    public WebApiConfig(AuthorInterceptor authorInterceptor, LogInterceptor logInterceptor) {
        this.authorInterceptor = authorInterceptor;
        this.logInterceptor = logInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        String path = "/**";
        String[] excludePath = {"/api/v1/user/register", "/api/v1/user/login"};

        // token验证拦截器实例
        registry.addInterceptor(authorInterceptor)
                .addPathPatterns(path)
                .excludePathPatterns(excludePath);

        // 日志输出拦截器实例
        registry.addInterceptor(logInterceptor)
                .addPathPatterns(path);
    }

    /**
     * 跨域请求处理
     * @param registry registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://127.0.0.1:3000", "http://localhost:8080", "http://52.83.246.220:9001")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }
}
