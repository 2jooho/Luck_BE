package com.example.luck_project.common.config.constants;

public class RedisKey {
    private RedisKey() {}
    private static final String DIVIDER = "|";
    public static final String JWTTOKEN_PREFIX = "";

    public static String jwtTokenKey(String... val) {
        return JWTTOKEN_PREFIX + keyAppender(val);
    }
    public static String keyAppender(String ...value) {
        StringBuilder append = new StringBuilder();
        for (String val : value) {
            append.append(DIVIDER).append(val);
        }
        return append.toString();
    }
}
