package com.example.mongo.config;

import com.example.mongo.app.common.filter.InputValidationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.terasoluna.gfw.common.exception.ExceptionLogger;
import org.terasoluna.gfw.common.exception.SimpleMappingExceptionCodeResolver;
import org.terasoluna.gfw.web.exception.ExceptionLoggingFilter;

import java.util.LinkedHashMap;

@Configuration
public class Config {


    @Value("${app.security.prohibitedChars}")
    private char[] prohibitedChars;

    @Value("${app.security.prohibitedCharsForFileName}")
    private char[] prohibitedCharsForFileName;

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("i18n/application-messages");
        source.setDefaultEncoding("UTF-8");
        return source;
    }

    @Bean
    public SimpleMappingExceptionCodeResolver exceptionCodeResolver() {
        SimpleMappingExceptionCodeResolver resolver = new SimpleMappingExceptionCodeResolver();
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("ResourceNotFoundException", "e.sl.fw.5001");
        map.put("MultipartException", "e.sl.fw.6001");
        map.put("InvalidTransactionTokenException", "e.sl.fw.7001");
        map.put("BusinessException", "e.sl.fw.8001");
        map.put("DataAccessException", "e.sl.fw.9002");
        resolver.setExceptionMappings(map);
        resolver.setDefaultExceptionCode("e.sl.fw.9001");
        return resolver;
    }

    @Bean
    public ExceptionLogger exceptionLogger() {
        ExceptionLogger logger = new ExceptionLogger();
        logger.setExceptionCodeResolver(exceptionCodeResolver());
        return logger;
    }

    @Bean
    public ExceptionLoggingFilter exceptionLoggingFilter() {
        ExceptionLoggingFilter filter = new ExceptionLoggingFilter();
        filter.setExceptionLogger(exceptionLogger());
        return filter;
    }

    @Bean
    public InputValidationFilter inputValidationFilter() {
        return new InputValidationFilter(prohibitedChars, prohibitedCharsForFileName);
    }

}
