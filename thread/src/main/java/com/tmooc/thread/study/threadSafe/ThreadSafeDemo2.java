package com.tmooc.thread.study.threadSafe;

import ch.qos.logback.core.pattern.FormatInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cuitao
 * @ className:ThreadSafeDemo2
 * @ description:线程安全
 * 实现案例：
 * 多线程调用,计数任务
 * @ create 2021-03-06 15:00
 **/
public class ThreadSafeDemo2 {
    /**
     * 1.创建计数任务对象
     * 2.多线程调用计数任务
     * 3.判断对线程运行结束，获取计数结果
     * 4.分析计数结果不一致原因
     * 5.添加volatile 防止指令重排序
     * 6.添加synchronized 保证原子性
     */
    // 1.创建计数任务对象
    public static class CountObject implements Runnable {
        private volatile int count ;


        @Override
        public void run() {
            //设置计数任务
            try {
                doCount();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public synchronized void doCount() throws InterruptedException {
            for (int i = 0; i < 1000; i++) {
                count++;
            }
        }


        public int getCount() {
            return count;
        }
    }

    public static void main(String[] args) {
        //创建计数任务对象实例
        CountObject countObject = new CountObject();
        List<Thread> threadList = new ArrayList<>();
        // 2.多线程调用计数任务
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(countObject);
            threadList.add(thread);
        }
        threadList.stream().forEach(
                thread -> thread.start()
        );
        //3.判断对线程运行结束，获取计数结果
        while (Thread.activeCount() > 1) {//线程激活数
            Thread.yield();//(主线程)当前线程放弃运行，回到就绪状态
        }
        int count = countObject.getCount();
        System.out.println("计数结果： " + count); // 905，1000,800 ...
        // 4.分析计数结果不一致原因
        /**
         * 多线程操作 count++； 使得一个线程的  count ++ ,出现了数据安全问题，只需要加把锁防止多个线程同时操作count++即可
         * 落地方法：
         * 1.   synchronized (this) {count++; }  最本质的方法，让count ++ ,完全实现原子操作即可
         * 2.   方法1，是做本质的做法，但是锁的的粒度太小了，每次技术都要加锁，释放锁， 其实只要做到不同线程之间排序执行（同步）就能防止
         *      线程安全问题，所以可以把锁放在不同线程访问的入口，即达到线程安全的效果，也减少了锁的平凡打开释放带来的io消耗
         *       public synchronized void doCount() throws InterruptedException {
         *             for (int i = 0; i < 100; i++) {
         *                 count++;
         *             }
         *         }
         * 3.上述两个方法，有效防止了不同线程之间争夺资源，导致的数据异常，但是同一个线程内部代码的执行顺序变化，即指令重排序问题，
         *     带来的数据不一致，并不能解决，这个时候就要用到 volatile 来防止指令重排序了,所以就会有
         *             private volatile int count ;
         */
        //小结：  synchronized 保证了原子性，防止了不同线程之间因为争夺资源导致的数据不安全。策略就是让多线程并发执行，也即同步，
        //      同时单线程内部保持原子性
        //      volatile 可以保证单线程内部的程序执行顺序一致，不产生重排序，也就是防止质量重排序带来的线程安全问题
    }
}
