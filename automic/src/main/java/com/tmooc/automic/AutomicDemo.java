package com.tmooc.automic;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.LongStream;

/**
 * @author cuitao
 * @ className:
 * @ description:
 * @ create 2021-04-10 13:57
 **/
public class AutomicDemo {

    public static void main(String[] args) {
        //类似于i++ ,++i 的情况，在多线程的情况下是线性不安全的，解决的办法就是CAS算法或者分段加锁
        //其中CAS因为是自旋，相当于死循环，耗资源较大，所以适用于竞争不太激烈的情况下
        //分段加锁更适合于竞争激烈的情况下，比如单线程且资源竞争激烈事适用
        //CAS算法
        AtomicLong atomicLong = new AtomicLong();
        LongStream.range(0, 1000).parallel().forEach((t) ->atomicLong.incrementAndGet());
        System.out.println("atomicLong="+atomicLong);
        //分段加锁
        LongAdder longAdder = new LongAdder();
        LongStream.range(0,2000).parallel().forEach((t)->longAdder.increment());
        long sum=longAdder.sumThenReset();
        System.out.println("sum="+sum);
    }
}
