package com.example.luck_project.common.util;


public class ResultCode {

    /** Message : [000] 정상 처리되었습니다. */
    public static final String SUCCESS = "000";

    /** Message : [001] 액세스 토큰이 유효하지 않습니다. */
    public static final String INVALID_TOKEN = "001";

    /** Message : [002] 액세스 토큰의 기간이 만료되었습니다. */
    public static final String NO_SIRN_TOKEN = "002";

    /** Message : [003] 요청 파라미터의 값이 잘못되었습니다. */
    public static final String INVALID_PARMETER = "003";

    /** Message : [004] 잘못된 URL입니다. */
    public static final String INVALID_URL = "004";

    /** Message : [005] 잘못된 요청입니다. */
    public static final String INVALID_REQUEST = "005";

    /** Message : [006] 프로토콜 오류입니다. (HTTPS) */
    public static final String INVALID_PROTOCOL = "006";

    /** Message : [007] 허용되지 않은 HTTP 메소드 입니다. (허용 메소드 : POST, GET, PATCH, DELETE) */
    public static final String INVALID_METHOD = "007";

    /** Message : [008] DB 오류입니다. */
    public static final String DB_ERROR = "008";

    /** Message : [009] 조회된 데이터가 없습니다. */
    public static final String NO_DATA = "009";

    /** Message : [010] 서버 작업 중 입니다. */
    public static final String SERVER_WORKING = "010";

    /** Message : [011] json data가 유효하지 않습니다. */
    public static final String JSON_DATE_ERROR = "011";

    /** Message : [099] 기타 오류입니다. */
    public static final String SERVER_ERROR = "099";


    /** Message : [101] 매장코드가 존재하지 않습니다. */
    public static final String NO_STORE = "101";

}