package com.tmooc.stream.study;

import sun.plugin2.message.GetAppletMessage;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

/**
 * @author cuitao
 * @ className:StreamEndOperation
 * @ description:stream终止操作
 * @ create 2021-02-28 15:31
 **/
public class StreamEndOperation {
    /**
     * match操作,做判断，返回boolean 值
     */
    //allMatch ：判断stream中所有元素是否都匹配，是->return true，否->return false
    //anyMatch : 判断stream中是否有任意一个符合匹配要求，是->return true，否->return false
    //noneMatch : 判断stream中是否都不符合匹配要求，是->return true，否->return false
    /**
     * find操作
     */
    //findFirst:  查找stream 中满足条件的第一个
    //findAny:    查找stream 中满足条件的任意一个
    /**
     * count   对stream 中元素做统计
     */
    /**
     * max ，min  获取最大，最小值
     */
    /**
     * foreach 迭代操作
     */
    /**
     * Reduce(约规)操作
     */
    /**
     * Collector 收集操作
     */


    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(10, 11, 10, 12, 15);
        //match操作
        boolean flag = list.stream().allMatch(e -> e % 2 == 0); //false
        flag = list.stream().anyMatch(e -> e % 2 == 0);//true
        flag = list.stream().noneMatch(e -> e % 2 == 0);//false
        //find 操作
        Optional<Integer> optionalInteger = list.stream().sorted().findFirst();//Optional[10]
        optionalInteger = list.stream().distinct().findAny();//获取任何一个，但是默认是返回的是第一个 这很尴尬
        System.out.println(optionalInteger.get());// 10
        //count 操作
        long count = list.stream().count();
        System.out.println(count);
        //max ，min   max || min 中调用的是比较器 Comparator ，比较器当然两个入参做比较的对象

        Optional<Integer> maxVale = list.stream().max(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                //自己定义的比较算法
                return 01 - 02;
            }
        }); //Optional[15]
        //lambda 表达式简化写法
        Integer max = list.stream().max((o1, o2) -> 01 - 02).get();// 15
        Integer min = list.stream().min((o1, o2) -> o1 - o2).get();//10
        //reduce 操作, reduce 的初始值可有可无，初始值的目的是后面的规约操作的结果和初始值做同样的操作
        //给定初始值，得到stream中各个元素的加和后再加上出初始值  为啥要加不是乘除等操作，因为reduce 规定规约结果和初始值间的操作和规约操作保持一致
        Integer sum = list.stream().reduce(1, (x, y) -> x + y);//59
        Optional<Integer> cehgnji = list.stream().reduce((x, y) -> x * y);
        System.out.println("cehgnji:" + cehgnji.get());  //198000
        //reduce 注意点，因为这个操作是有叠加，叠乘等操作，很容易超界，所以又有了格式转换的操作
        Long longRes = list.stream().reduce(100L, (x, y) -> x * y, (x, y) -> 0L);//100L，OL 这种格式是将计算结果转换为Long 类型，防止结果超界
        //Collectors 收集操作
        list.stream().collect(Collectors.toList());
        list.stream().collect(Collectors.toSet());
        //分组后的返回值类型：Map<Object, List<Integer>>
        Map<Object, List<Integer>> objectMap = list.stream().collect(Collectors.groupingBy(x -> x % 2 == 0));
        System.out.println(objectMap); //{false=[11, 15], true=[10, 10, 12]}
        double avenue = list.stream().collect(Collectors.averagingDouble(new ToDoubleFunction<Integer>() {
            @Override
            public double applyAsDouble(Integer value) {
                //自定义求平均值的算法
                return value;
            }
        }));
        System.out.println(avenue);  //11.6
        //简化版
        double avenue2 = list.stream().collect(Collectors.averagingDouble(x -> x));
        System.out.println(avenue2);//11.6
        //获取一个盘符底下的所有目录名
        List<String> allDirFileNames = Arrays.stream(new File("D:\\code\\").listFiles()).filter(File::isDirectory)
                .map(File::getName).collect(Collectors.toList());
        System.out.println(allDirFileNames);//[AndroidUnitTest-master, car-manage, ChinaUnicom, funny-test-master, java-patterns, kd-manage-master, kd-member-master, my-migu, powermock-mockito-junit-1.6.3, powermock-release-2.x, powermockCuitao, shardingsphere-master, test-spring-cloud-master, tmooc, tmooc-Lessons, xxl-job-logDir, xxl-job-master, yapi, 我的svn仓库]


    }
}

