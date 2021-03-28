package com.tmooc.stream.study;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author cuitao
 * @ className:StreamMediumOperation
 * @ description: stream的中间操作
 * @ create 2021-02-28 11:45
 **/
@Slf4j
public class StreamMediumOperation {
    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(10, 20, 30, 41, 30, 40, 40);
        //  filter
        //过滤： filter (),filter 返回的是Stream对象，filter中引用的 是 Predicate这个匿名内部类[有入参，返回一个boolean值，默认是false]
        //  整体来看 filter 返回的是 经过 Predicate处理返回为 true 的元素组成的流

        // limit 只对stream 中数据，做数量的限制，返回的仍然是stream
        list.stream().filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return false;
            }
        });
        Stream<Integer> stream = list.stream().filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                if (integer % 2 == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        stream.forEach(System.out::println);
        //lambda 简化版
        Stream<Integer> stream1 = list.stream().filter(e -> e % 2 == 0);
        List<Integer> list1 = list.stream().filter(e -> e % 2 == 0).collect(Collectors.toList());
        long count = list.stream().filter(e -> e % 2 == 0).limit(2).collect(Collectors.toList()).stream().count();
        System.out.println(count);
        log.info("====================================================================");
        //  skip  跳过stream中的几个数据，从对应stream 流中第一个开始计算,返回的还是流
        List<Integer> list2 = list.stream().skip(2).collect(Collectors.toList()); // list: [10, 20, 30, 41,30,40,40]  list2: [30, 41, 30, 40, 40] 跳过了开头两个
        log.info("====================================================================");
        //  distinct()  没有入参，直接对流中的数据做去重，返回去重后的流
        List<Integer> list3 = list.stream().distinct().collect(Collectors.toList()); //[10, 20, 30, 41, 40]
        log.info("====================================================================");
        // sorted 排序，是将流中数据做排序操作，排序后返回新的stream，分两种：默认的排序算法，和自定义排序算法
        // 默认排序算法：是按照排序的每一个元素的第一个字符的ASCII码值做升序排序
        // 自定义排序算法：用到的是匿名内部类，即函数是接口Comparator
        List<String> list4 = Arrays.asList("!", "A", "a", "bb", "c", "b", "afff");
        //默认排序算法
        list4.stream().sorted().collect(Collectors.toList());//[!, A, a, afff, b, bb, c]
        //自定义排序算法
        Stream<String> stringStream = list4.stream().sorted(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                //这里自定义排序算法
                return o1.compareTo(o2);
            }
        });
        List<String> stringList = stringStream.collect(Collectors.toList());// [!, A, a, afff, b, bb, c]
        //lambda 表达式简化版
        List<String> stringList1 = list4.stream().sorted((o1, o2) -> o1.compareTo(o2)).collect(Collectors.toList());
        log.info("===================================================================");
        // map 映射，map 中调用的是函数式接口Function<T,R>我们可以通过定义入参类型T,和返回值类型R,调用抽象方法apply实现对入参想要的处理结果
        List<String> list5 = Arrays.asList("!", "A", "a", "bb", "c", "b", "afff");
        Stream<Object> objectStream = list5.stream().map(new Function<String, Object>() {
            @Override
            public Object apply(String s) {
                //对参数做处理，得到想要的值
                return s.length();
            }
        });
        System.out.println(objectStream.collect(Collectors.toList()));//[1, 1, 1, 2, 1, 1, 4]
        //lambda 简化版本
        List<Integer> integerList = list5.stream().map(s -> s.length()).collect(Collectors.toList());//[1, 1, 1, 2, 1, 1, 4]
        list5.stream().map(s->s.length()).forEach(e->System.out.println(e));
        log.info("======");
        list5.stream().map(s->s.length()).forEach(System.out::println);
    }
}
