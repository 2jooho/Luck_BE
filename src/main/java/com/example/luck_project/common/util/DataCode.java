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

    public static Integer getCodeNum(String[] selectArr, String code) {
        Integer codeNum = 0;

        if(code == null) return null;
        if(selectArr == null) return null;

        for (String arr : selectArr){
            codeNum++;
            if(code.equals(arr)){
                break;
            }
        }

        return codeNum;
    }

    //비장술 명칭
    public static final String[] PURE_LUCK_NAME_ARR = {"강일진", "천록", "사살신", "합식", "기러기", "공망신", "약일충", "원진록", "해결신", "퇴식", "금조건", "백병주"};

    //띠 명칭
    public static final String[] VERS_YEAR_NAME_ARR = {"자", "축", "인", "묘", "진", "사", "오", "미", "신", "유", "술", "해"};

    public static final String[] VERS_YEAR_TIME_ARR = {"23:00~01:00", "01:00~03:00", "03:00~05:00", "05:00~07:00", "07:00~09:00", "09:00~11:00", "11:00~13:00", "13:00~15:00", "15:00~17:00", "17:00~19:00", "19:00~21:00", "21:00~23:00"};
    //비장술 상태 코드명
//    public static final String[][] PURE_LUCK_STATUS_ARR = {{}, {}};

    //띠 상태 코드명
//    public static final String[][] VERS_YEAR_STATUS_ARR = {{}, {}};

}