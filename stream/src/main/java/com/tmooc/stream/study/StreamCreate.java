package com.tmooc.stream.study;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @author cuitao
 * @ className:StreamDemo
 * @ description:流操作
 * 1.作用
 * 使得原本在数据库层面才能做的过滤，收集，统计，分组等操作，可以方便的在业务层面操作
 * 2.创建方式
 * 1>Collection接口的stream()方法，或parallelStream()方法
 * 2>Arrays类中的stream(...)方法
 * 3>Stream类中的of(...)方法
 * 4>Stream类中的iterator和generator方法（无限操作）
 * 3.Stream案例分析示例
 * Collection<Ingeter> col = new ArrayList<>();
 * Stream<Integer>s1 = col.stream();
 * Stream<Integer> s2=col.parallelStream();
 * IntStream s3 = Arrays.stream(new int []{1,2,3});
 * Stream<Integer> s4 = Stream.of(10,20);
 * Stream<Integer>s5 =Stream.iterate(2,(x)->x+2)； 无线迭代下去
 * Stream<Double>s6=Stream.generate(()->Math.random())
 * @ create 2021-02-27 18:15
 **/
@Slf4j
public class StreamCreate {
    public static void main(String[] args) {
        //=======================================获取Stream对象========================================
        // 1>Collection接口的stream()方法，或parallelStream()方法
        List<Integer> list = Arrays.asList(10, 20, 30, 40, 50);
        //1.创建顺序，迭代流，输出流
        list.stream().forEach(e -> {
            System.out.println(e);
        });
        System.out.println("================");
        list.stream().forEach(System.out::println);
        System.out.println("================");
        //2.并行流创建，迭代，输出 [并行流会利用CPU多核的特点，多线程运行流,至于线程创建的动作在底层封装了，所以并行流适合于海量数据的流处理]
        list.parallelStream().forEach(System.out::println);

        // 2>Arrays类中的stream(...)方法
        LongStream longStream = Arrays.stream(new long[]{1l, 2l, 3l});
        longStream.forEach(System.out::println);
        // 3>Stream类中的of(...)方法
        List<String> list1 = Stream.of("a", "b", "c").collect(Collectors.toList());
        log.info(list1.toString());
        //
        //4>Stream类中的iterator和generator方法（无限操作）
        // Stream.iterate(1, t -> t + 1).forEach(System.out::println);//需要入参，或叫种子，基于种子无限生产数据
        //Stream.generate(()->Math.random()).forEach(System.out::println);// 不许要入参，或种子，无限生成数据
        Stream.generate(() -> UUID.randomUUID().toString()).forEach(System.out::println);
    }

}
