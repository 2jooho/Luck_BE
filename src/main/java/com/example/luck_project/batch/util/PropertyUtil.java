package com.example.luck_project.batch.util;//package co.kr.luckBatch.util;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.env.Environment;
//
//@Configuration
//@PropertySource(value = "classpath:properties/dev.properties", encoding="UTF-8")
//public class PropertyUtil {
//    @Autowired
//    private transient Environment environment;
//
//    public PropertyUtil() { }
//
//    public String getProperty(String code) {
//        return StringUtils.defaultString(environment.getProperty(code), "");
//    }
//
//    public String getProperty(String code, String defaultValue) {
//        String value = (String) environment.getProperty(code);
//        return StringUtils.isBlank(value) ? defaultValue : value;
//    }
//
//    public <T> T getProperty(String code, Class<T> clazz) {
//        return (T) environment.getProperty(code);
//    }
//
//    public <T> T getProperty(String code, T defaultValue, Class<T> clazz) {
//        Object value = environment.getProperty(code);
//
//        if (Integer.class.getName().equals(clazz.getName())) {
//            return (T) ((value == null) ? defaultValue : Integer.parseInt((String) environment.getProperty(code)));
//        } else {
//            return (value == null) ? defaultValue : (T) environment.getProperty(code);
//        }
//
//    }
//
//}