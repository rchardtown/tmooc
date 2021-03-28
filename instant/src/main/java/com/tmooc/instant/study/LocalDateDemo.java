package com.tmooc.instant.study;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author cuitao
 * @ className:LocalDateDemo
 * @ description: 日期对象： 年月日
 * @ create 2021-03-01 7:48
 **/
public class LocalDateDemo {
    public static void main(String[] args) {
        //获取LocalDate对象
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);//2021-03-01
        //字符串转换为LocalDate对象
        LocalDate localDate1 = LocalDate.parse("2021-03-01");
        System.out.println(localDate1);//2021-03-01
        //自定义要解析为LocalDate 对象的字符串的格式，解析最后的格式是标准格式yyyy-MM-dd
        LocalDate localDate2 = LocalDate.parse("2019/12/20", DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        System.out.println(localDate2);//2019-12-20


    }
}
