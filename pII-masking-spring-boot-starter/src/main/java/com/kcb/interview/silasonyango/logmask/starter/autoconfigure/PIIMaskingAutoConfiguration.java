package com.kcb.interview.silasonyango.logmask.starter.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kcb.interview.silasonyango.logmask.starter.aop.LoggingMaskingAspect;
import com.kcb.interview.silasonyango.logmask.starter.config.PIIMaskingProperties;
import com.kcb.interview.silasonyango.logmask.starter.core.PIIMaskingService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(PIIMaskingProperties.class)
@ConditionalOnClass(ObjectMapper.class)
@ConditionalOnProperty(prefix = "pii.masking", name = "enabled", havingValue = "true", matchIfMissing = true)
public class PIIMaskingAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public PIIMaskingService piiMaskingService(ObjectMapper objectMapper, PIIMaskingProperties properties) {
        return new PIIMaskingService(objectMapper, properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public LoggingMaskingAspect loggingMaskingAspect(PIIMaskingService maskingService) {
        return new LoggingMaskingAspect(maskingService);
    }
}

