package com.example.luck_project.controller.constants;

public class ApiUrl {
    private ApiUrl(){}

    public static final String BASE_URL = "/luck";
    public static final String LOGIN_URL = "/auth/login";
    public static final String JOIN_URL = "/auth/join";
    public static final String OAUTH_JOIN_URL = "/auth/{socialLoginPath}/join";
    public static final String ID_CHECK_URL = "/auth/idCheck";
    public static final String CATE_DETAIL_LIST_URL = "/cateDetailList.do";
    public static final String MAIN_URL = "/main.do";
    public static final String PURE_LUCK_MAIN_URL = "/pureLuckMain.do";
    public static final String AUTH_TYPE_LOGIN_URL = "/auth/{socialLoginType}/login";
    public static final String AUTH_CALLBACK_URL = "/auth/{socialLoginType}/callback";
    public static final String AUTH_NICKNAME_CHECK_URL = "/auth/nickNameCheck";
    public static final String AUTH_USER_SETTING_URL = "/auth/userSetting";
    public static final String AUTH_BOLTER_URL = "/auth/bolter";
    public static final String AUTH_ID_FIND_URL = "/auth/find/id";
    public static final String AUTH_RESET_PW_USER_INFO_URL = "/auth/reset/password/userInfo";
    public static final String AUTH_RESET_PW_URL = "/auth/reset/password";
    public static final String MYPAGE_URL = "/myPage.do";

    public static final String BASE_URL_BATCH = "/luckBatch";
    public static final String FCM_TOPICS_URL = "/pushs/topics/{topic}";
}
