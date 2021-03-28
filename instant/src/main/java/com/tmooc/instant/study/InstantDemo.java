package com.tmooc.instant.study;

import org.springframework.aop.scope.ScopedProxyUtils;

import java.time.*;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author cuitao
 * @ className:
 * @ description:
 * @ create 2021-03-01 6:59
 **/
public class InstantDemo {

    public static void main(String[] args) throws InterruptedException {

        //获取时钟对象
        Clock clock = Clock.systemUTC();
        System.out.println("clock : " + clock);
        //获取零时区时间
        Instant instant = clock.instant();
        System.out.println("0时区时间 : " + instant);
        //加成东八区当前时间
        instant = instant.plusMillis(TimeUnit.HOURS.toMillis(8));
        System.out.println("东八区时间: " + instant);
        //获取时间戳
        long timeStamp = clock.millis();
        System.out.println("时间戳：" + timeStamp);
        //获取系统支持的时区集合
        Set<String> setString = ZoneId.getAvailableZoneIds();
        System.out.println("系统支持的时区集合：" + setString);
        //获取系统默认时区，即当前用户所在时区
        ZoneId zoneId = ZoneId.systemDefault();
        System.out.println("系统默认时区：" + zoneId);
        //获取系统默认时区和该时区的时间
        ZonedDateTime zonedDateTime = Instant.now().atZone(ZoneId.systemDefault());
        System.out.println("系统默认时区和该时区的时间：" + zonedDateTime);
        //Instant.now() 对象 == Clock.SystemUTC.instant()
        Instant instant1 = Clock.systemUTC().instant();
        Instant instant2 = Instant.now();
        System.out.println("Instant.now() 对象 == Clock.SystemUTC.instant() " + (instant1.getClass() == instant2.getClass()));
        //获取时间间隔
        Instant start = Clock.systemUTC().instant();
        Thread.sleep(2000);
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        long durationDays = duration.toDays();
        System.out.println("时间差 天数：" + durationDays);
        long durationHour = duration.toHours();
        System.out.println("时间差 小时：" + durationHour);
        long duratonSeconds = duration.getSeconds();
        System.out.println("时间差 分钟：" + duratonSeconds);
        long durationMinutes = duration.toMinutes();
        System.out.println("时间差 秒：" + durationMinutes);
        long durationMillis = duration.toMillis();
        System.out.println("时间差 毫秒：" + durationMillis);

    }
}
