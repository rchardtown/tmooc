package com.tmooc.methodreference;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author cuitao
 * @ className:MethodReferenceDemo
 * @ description:方法引用
 * 1.理解
 * 方法引用本质是把一个方法作为一个参数传递给另一个方法，让其去调用
 * 2. 使用场景
 * 目标时调用A对象的a方法，现在发现，[B对象的b方法和A对象的a方法参数列表和返回值类型都相等 ]且  [a方法调用了b方法]，此时，在调用a方法时就可以，引用b方法
 * @ create 2021-02-27 15:37
 **/
@Slf4j
public class MethodReferenceDemo {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("1", "2", "3");
        list.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });
        //lambda表达式 简化后的调用
        list.forEach(e -> System.out.println(e));
        System.out.println("============================================");
        //方法引用案例1
        //1.目标是调用方法 accept ，现在因为 void accept(String s) 和 方法 void println(String s) 同时满足参数列表和返回值类型相等，且
        //accept(String s)方法中调用了println(String s)方法，所以可以用方法的引用
        //格式为， 被引用的方法的对象::被引用的方法
        list.forEach(System.out::println);
        System.out.println("============================================");
        //==========================方法引用 demo2========================
        Function<String, Integer> function = new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return Integer.parseInt(s);
            }
        };
        log.info(function.apply("100") + "");
        //简化为
        Function<String, Integer> function1 = e -> {
            return Integer.parseInt(e);
        };
        log.info(function1.apply("100") + "");
        //发现调用的方法apply的参数列表和 Integer apply（String s）中调用的方法Integer parseInt(String s),参数列表和返回值类型相等，且
        //apply 中调用了 parseInt,故在调用apply 方法时 可做方法引用.方法引用可代替整个 lambda 表达式
        Function<String, Integer> function2 = Integer::parseInt;
        log.info(function2.apply("100") + "");
        function2.apply("100");
        //==========================方法引用 demo3========================
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };
        //lambda表达式简化为
        Comparator<Integer> comparator1 = (o1, o2) -> Integer.compare(o1, o2);
        //将lambda 表达式方式改为方法引用方式
        Comparator<Integer> comparator2 = Integer::compare;
        //调用一下
        int res = comparator2.compare(0, 2);
        log.info(res + "");
    }

}
