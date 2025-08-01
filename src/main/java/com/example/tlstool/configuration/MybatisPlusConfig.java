package com.example.tlstool.configuration;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.example.tlstool.handler.JsonNodeTypeHandler;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;

@Configuration
public class MybatisPlusConfig {

    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return configuration -> {
            configuration.setDefaultScriptingLanguage(XMLLanguageDriver.class);
            // 注册后所有JsonNode字段自动使用处理器
            configuration.getTypeHandlerRegistry().register(JsonNode.class, JsonNodeTypeHandler.class);
        };
    }
}