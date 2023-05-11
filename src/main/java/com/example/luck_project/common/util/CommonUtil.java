package com.example.luck_project.common.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class CommonUtil {
    private final static int LENGTH_10_INT_RADIX = 7;
    // 10자리의 UUID 생성
    public static String makeShortUUID() {
        UUID uuid = UUID.randomUUID();
        return parseToShortUUID(uuid.toString());
    }
    // 파라미터로 받은 값을 8자리의 UUID로 변환후 uuid 앞 6자리+ 변환숫자 조합 리턴
    public static String parseToShortUUID(String uuid) {
        String uuidStr = uuid.substring(0, 6);
        int l = ByteBuffer.wrap(uuid.getBytes()).getInt();
        return uuidStr.toUpperCase()+Integer.toString(l, LENGTH_10_INT_RADIX);
    }
}
