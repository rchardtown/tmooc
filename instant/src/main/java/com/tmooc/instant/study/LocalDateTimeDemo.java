package com.tmooc.instant.study;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

/**
 * @author cuitao
 * @ className:LocalDateTimeDemo
 * @ description: 日期时间
 * @ create 2021-03-01 19:40
 **/
public class LocalDateTimeDemo {
    public static void main(String[] args) {
        //获取时间
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);//2021-03-01T19:45:24.723
        //自定义指定格式的LocalDateTime对象
        LocalDateTime localDateTime1 = LocalDateTime.of(2021, 12, 31, 12, 56, 23);
        System.out.println(localDateTime1);//2021-12-31T12:56:23
        //一周的第几天，星期几
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        System.out.println(dayOfWeek);//MONDAY
        //一月中的第几天
        long month = localDateTime.getDayOfMonth();
        System.out.println(month);
        //一年中的第几天
        long year = localDateTime.getDayOfYear();
        System.out.println(year);
    }
}
