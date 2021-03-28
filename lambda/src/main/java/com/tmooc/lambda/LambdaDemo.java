package com.tmooc.lambda;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author cuitao
 * @ className:LambdaDemo
 * @ description: lambda表达式
 * 理解：
 * 实质是只保留方法的参数和函数主体，其他的部分如：访问修饰符，返回值类型，方法名等省略
 * 如果将默认的函数式接口放在方法中使用的时，通常将这中函数式接口称为匿名内部类
 * @ create 2021-02-27 13:59
 **/
public class LambdaDemo {

    public static void main(String[] args) {
        String[] array1 = new String[]{"1", "2"};
        String[] array2 = new String[]{"a", "b"};
        String[] array3 = new String[]{"A", "B"};
        List<String[]> list = Arrays.asList(array1, array2, array3);
        //迭代方法1
        for (String[] li : list) {
            System.out.println(li[0]);
        }
        System.out.println("=============================");
        //迭代方法2 foreach + 函数式接口
        list.forEach(r -> {
            Consumer<String[]> consumer = new Consumer<String[]>() {
                @Override
                public void accept(String[] strings) {
                    System.out.println(strings[0]);
                }
            };
            consumer.accept(r);
        });
        System.out.println("=============================");
        //迭代方法3 foreach + lambda 表达式   ( )：中存放函数式接口中抽象方法的参数列表，或这要迭代的对象    -> ：操作符，对 e 的处理逻辑
        //注意：
        //  1.  e 的类型可以不写，java 底层自己会推算出e的类型；
        //  2.  当（） 中只有一个对象时，（） 也可以不写，当没有参数时必须写（），此时（）中没有东西
        //  3.  对e 的处理只有一条语句时  {} 也可以不写
        list.forEach((String[] e) -> {
            System.out.println(e[0]);
        });
        //所以上述的写法可以简化为下面的两种，最终选择追简单的就行
        list.forEach((e) -> {
            System.out.println(e[0]);
        });
        list.forEach(e -> {
            System.out.println(e[0]);
        });
        list.forEach(e -> System.out.println(e[0]));
        System.out.println("==================================");
        /**
         * 当函数式接口为有返回值的接口时，如果代码块中只有一条语句，此时可以不写return
         */
        //原生方法：调用Comparetor 函数式接口处理
        List<String> stringList = Arrays.asList("a", "c", "d", "b");
        stringList.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
//                return 0;
                return o1.compareTo(o2);
            }
        });
        System.out.println(stringList);
        //简化方法
        List<String> stringList2 = Arrays.asList("a", "c", "d", "b");
        stringList2.sort((o1, o2) -> {
            return o1.compareTo(o2);
        });
        System.out.println(stringList);
        //函数体只有一条语句，此时又有返回值，可以省略 reurn
        List<String> stringList3 = Arrays.asList("a", "c", "d", "b");
        stringList3.sort((o1, o2) -> o1.compareTo(o2));
        System.out.println("==================================");
        //创建一个线程 :原生
        //创建一个线程不执行任务： new Thread().start()
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程内执行任务的代码块");
            }
        }).start();
        //创建一个线程 :lambda 表达式,即：访问修饰符，类名，方法名，返回值了类型都不用了,没有参数了用（）表示
        new Thread(() -> {System.out.println("线程内执行任务的代码块");}).start();
        new Thread(() -> System.out.println("线程内执行任务的代码块")).start();
    }


}
