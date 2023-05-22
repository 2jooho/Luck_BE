
const AUTH = Object.freeze({
    LOG_IN: '/luck/auth/login',
    MAIN_PAGE: (userId: string) => `/luck/main.do?userId=${userId}`,
    MYPAGE: (userId: string) => `/luck/myPage.do?userId=${userId}`,
});

export const API_ROUTE = Object.freeze({
    AUTH,
});