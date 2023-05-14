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

    //띠 기준 시간
    public static final String[] VERS_YEAR_TIME_ARR = {"23:00~01:00", "01:00~03:00", "03:00~05:00", "05:00~07:00", "07:00~09:00", "09:00~11:00", "11:00~13:00", "13:00~15:00", "15:00~17:00", "17:00~19:00", "19:00~21:00", "21:00~23:00"};

    //사용자 태어난 시간
    public static final String[] USER_VERS_YEAR_TIME_ARR = {"23:30~01:29", "01:30~03:29", "03:30~05:29", "05:30~07:29", "07:30~09:29", "09:30~11:29", "11:30~13:29", "13:30~15:29", "15:30~17:29", "17:30~19:29", "19:30~21:29", "21:30~23:29"};

    //십이지명과 동물 매칭
    public static final String[][] VERS_YEAR_ANIMAL_MATCHING_ARR = {{"자", "쥐"}, {"축", "소"}, {"인", "호랑이"}, {"묘", "토끼"}, {"진", "용"}, {"사", "뱀"}, {"오", "말"}, {"미", "양"}, {"신", "원숭이"}, {"유", "닭"}, {"술", "개"}, {"해", "돼지"}};

    //비장술 상태 코드명
//    public static final String[][] PURE_LUCK_STATUS_ARR = {{}, {}};

    //띠 상태 코드명
//    public static final String[][] VERS_YEAR_STATUS_ARR = {{}, {}};

}