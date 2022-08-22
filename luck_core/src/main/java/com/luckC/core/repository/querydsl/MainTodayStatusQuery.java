package com.luckC.core.repository.querydsl;

public interface MainTodayStatusQuey {
    TodayLuckResponse findTodayLuck(String userId);
}