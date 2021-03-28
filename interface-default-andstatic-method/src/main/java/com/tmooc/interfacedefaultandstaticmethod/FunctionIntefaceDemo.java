package com.tmooc.interfacedefaultandstaticmethod;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author cuitao
 * @ className:FunctionIntefaceDemo
 * @ description: 函数式接口
 * 1定义：
 * 当且仅当，一个接口被@FunctionInterface注解，且接口中有且只有一个抽象方法的接口
 * 2注意：一旦被@FunctionInteface注解后，接口中有多个抽象方法就会报错
 * 3其他方法：
 * 函数时接口就中的静态方法，默认方法，
 * 4可看成函数式接口的接口：
 * 接口没有被@FunctionInterface注解，但是接口中只有一个抽象方法，这种接口也可以堪称函数式接口
 * 5存在意义：
 * 配和 lambda表达式 操作
 * 6 常见的几种函数是接口的分类
 * 6.1 消费性接口： 有入参没有返回值，如 Consumer<T>
 * 6.2 函数式接口： 有入参，入参在接口方法中计算后返回接口，如 Function<T,R>,其中T 是入参类型，R是返回值类型
 * 6.3 判定式样接口: 有入参，返回值是boolean 类型 如 Predicate
 * 6.4 供给式接口：  无参数，有返回值
 * @ create 2021-02-27 11:35
 **/
public class FunctionIntefaceDemo {
    public static void main(String[] args) {
        //创建函数式接口实例
        Fun fun = new Fun() {
            @Override
            public void domethod01() {
                System.out.println("函数式接口被成功调用");
            }
        };
        //调用函数式接口中唯一的抽象方法
        fun.domethod01();
        System.out.println("=======================================");
        //================================常见的集中函数式接口类型举例================================
        //消费型接口
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        consumer.accept("消费型接口成功被调用");
        System.out.println("=======================================");
        //函数式接口举例
        Function<String, Integer> function = new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return Integer.parseInt(s);
            }
        };
        Integer result = function.apply("100");
        System.out.println(result);
        consumer.accept("函数型接口成功被调用");
        System.out.println("=======================================");
        Predicate<String> predicate = new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return false;
            }
        };
        boolean res = predicate.test("测试");
        System.out.println(res);
        System.out.println("判定型接口被成功调用");
        System.out.println("=======================================");
        Supplier<Integer> supplier = new Supplier<Integer>() {
            @Override
            public Integer get() {
                return 0;
            }
        };
        Integer ress = supplier.get();
        System.out.println(ress);
        System.out.println("供给型接口被成功调用");
    }
}

@FunctionalInterface
interface Fun {
    //函数式接口中的唯一抽象方法
    void domethod01();

    //其他的都是一个或多个静态方法或则默认方法
    static int domethod2() {
        return 1;
    }

    default void domethod03() {
    }
}