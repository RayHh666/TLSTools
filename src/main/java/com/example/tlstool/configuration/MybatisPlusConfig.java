package com.example.tlstool.configuration;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
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

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        // 创建一个拦截器对象，
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        /**
         * 我们将 new 一个分页插件拦截器 PaginationInnerInterceptor 参数为数据库类型 MySQL,当然也可以写其他数据库类型，根据自己的数据库类型而定
         * 然后将此拦截器add添加到拦截器对象中即可生效
         * (这里演示MyBatis-Plus分页插件拦截器，其实还可以继续添加其他拦截器,我们可以继续 new ，继续add添加到拦截器对象中哦！)
         */
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return mybatisPlusInterceptor;
    }
}