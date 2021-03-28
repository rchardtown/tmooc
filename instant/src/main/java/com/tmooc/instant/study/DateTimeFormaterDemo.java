package com.tmooc.instant.study;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author cuitao
 * @ className:DateTimeFormaterDemo
 * @ description: 字符串和日期的线性安全转化
 *
 * 备注：传统的  SimpleDateFormat 格式化字符串线性不安全，JDK8后更新的 LocalDateTime 线性安全
 * @ create 2021-03-01 20:05
 **/
public class DateTimeFormaterDemo {
    public static void main(String[] args) {
        //获取日期对象
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);// 2021-03-01T20:06:50.651
        //日期对象转字符串
        String timeStr = localDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        System.out.println(timeStr);//2021/03/01 20:03:40:31
        //字符串对象以某种格式转换为日期对象 LocalDateTime
        localDateTime=  LocalDateTime.parse(timeStr,DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        System.out.println(localDateTime);


    }


}
