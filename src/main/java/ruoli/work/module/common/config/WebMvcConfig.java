package ruoli.work.module.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ruoli.work.module.common.interceptor.PermissionInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    //注册拦截器bean
    //tip:使用该种方式注册，可在拦截其中使用@AutoWired注解装配bean。使用其他方式（比如new PermissionInterceptor()）可能导致装配为null的情况
    //详见：https://blog.csdn.net/ycf921244819/article/details/91388440
    @Bean
    public PermissionInterceptor permissionInterceptor(){
        return new PermissionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(permissionInterceptor())
                //.excludePathPatterns("/permission/get")
                //添加拦截路径 /** ：拦截所有路径
                .addPathPatterns("/**").excludePathPatterns("/**/*.css","/**/*.js");
    }
}

