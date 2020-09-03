package com.example.mongo.config;

import com.example.mongo.app.common.RequestDataValueProcessorPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.servlet.support.csrf.CsrfRequestDataValueProcessor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.support.RequestDataValueProcessor;
import org.terasoluna.gfw.web.mvc.support.CompositeRequestDataValueProcessor;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenInterceptor;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenRequestDataValueProcessor;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    // @see https://qiita.com/tasogarei/items/9c3670201d4abb7276c4

    @Bean
    public TransactionTokenInterceptor transactionTokenInterceptor() {
        return new TransactionTokenInterceptor();
    }

    @Bean
    public RequestDataValueProcessor requestDataValueProcessor() {
        return new TransactionTokenRequestDataValueProcessor();

//        return new CompositeRequestDataValueProcessor(
//                new TransactionTokenRequestDataValueProcessor(),
//                new CsrfRequestDataValueProcessor()
//        );
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(transactionTokenInterceptor())
                .addPathPatterns("/**");
    }

    @Bean
    public RequestDataValueProcessorPostProcessor requestDataValueProcessorPostProcessor() {
        return new RequestDataValueProcessorPostProcessor();
    }
}