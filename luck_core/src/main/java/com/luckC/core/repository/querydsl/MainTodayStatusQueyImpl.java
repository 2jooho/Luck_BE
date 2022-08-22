package com.luckC.core.repository.querydsl;

public class MainTodayStatusQueyImpl  implements MainTodayStatusQuey {

    private final JPAQueryFactory queryFactory;


    public TodayLuckResponse findTodayLuck(String UserId){
        QUser quser = new QUser("u");
        Quser_luck_info quserLuckInfo = new Quser_luck_info("uli");
        Qtoday_luck_info qtodayLuckInfo = new Qtoday_luck_info("tli");

        return queryFactory
                .select(quser.userId)
                .from(quser)
                .join(quserLuckInfo).on(quser.userId.eq(quserLuckInfo.userId))
                .fetch();


        JPQLQuery<TodayLuckResponse> query = from(quser)

    }



}