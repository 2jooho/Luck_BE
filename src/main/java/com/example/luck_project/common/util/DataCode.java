package com.example.luck_project.common.util;


/**
 * 공통 코드 관리 DataCode
 */
public final class DataCode {

    public static String getCodeName(String[][] selectArr, String code) {
        String codeName = "";

        if(code == null) return codeName;
        if(selectArr == null) return code;

        for (String[] arr : selectArr){
            if(code.equals(arr[0])){
                codeName = arr[1];
                break;
            }
        }
        if("".equals(codeName)) codeName = code;

        return codeName;
    }

    //비장술 명칭
    public static final String[] PURE_LUCK_NAME_ARR = {"강일진", "천록", "사살신", "합식", "기러기", "공망신", "약일충", "원진록", "해결신", "퇴식", "금조건", "백병주"};

    //띠 명칭
    public static final String[] VERS_YEAR_NAME_ARR = {"자", "축", "인", "묘", "진", "사", "오", "미", "신", "유", "술", "해"};

    //비장술 상태 코드명
//    public static final String[][] PURE_LUCK_STATUS_ARR = {{}, {}};

    //띠 상태 코드명
//    public static final String[][] VERS_YEAR_STATUS_ARR = {{}, {}};

}