package com.tmooc.thread.study;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author cuitao
 * @ className:LockDemo
 * @ description: 线程锁
 * @ create 2021-03-07 10:47
 **/
public class LockDemo {
    /**
     * synchronized ： 是一种非公平锁，当对象被锁时，优先级低的线程很难抢占到资源，执行次数很低，甚至为0
     * Lock         ： 是一种公平，非公平锁，也就是它既可以支持公平，也可以支持非公平机制，当支持公平机制时，优先级低的锁就有了
     * 很多机会去抢到资源
     */


    //线性安全的 非公平锁
    class SynchronizedDemo {
        private volatile int count; // volatile 防止指令重排序

        public void count() {
            synchronized (this) {
                count++;
            }
        }
    }

    // 线性安全的 公平锁
    class LockTest {
        private volatile int count;
        // 创建非公平锁
        //Lock lock = new ReentrantLock(false);
        //创建公平锁
        Lock lock = new ReentrantLock(true);

        public void count() {
            //加锁
            try { //为了保证一定释放锁，必须用try,finally 包住
                lock.lock();
                count++;
            } finally {
                //释放锁
                lock.unlock();
            }
        }
    }

    //CAS :
    // java.util.concurrent.atomic包底下的类，底层都用的是CAS的算法，直接调用就行
    class CASdemo {
        private AtomicInteger atomicInteger = new AtomicInteger();

        public int getcount() {
            return atomicInteger.incrementAndGet();
        }
    }

    static class ThreadLocalDemo {
        //创建一个threadLocal 对象,此时每个线程中都会存储一份ThreadLocal<SimpleDateFormat>的实例的副本
        //以后哪个线程去调用SimpleDateFormat 的方法，哪个线程用的是自己线程内部的副本实例，各个线程之间相互独立
        private static ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<>();

        public static String returnDateStr(Date date) {
            //  sdf.get() 方法获取的是 当前线程  Thread.currentThread()的中的SimpleDateFormat 实例
            // 那么 当前线程是如何保证获取的是 它的 SimpleDateFormat 实例，而不是其他实例呢？其实它的ThreadLocalMap的key用的是this,
            // 对应的也就是当前线程 中的 sdf 实例，至此保证了 sdf.get()获取的是当前线程的 SimpleDateFormat实例 而不是其他实例
            SimpleDateFormat simpleDateFormat = sdf.get();
            if (!Objects.isNull(simpleDateFormat)) {
                String dateStr = simpleDateFormat.format(date);
                return dateStr;
            }
            System.out.println("创建SimpleDateFormat实例");
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            return simpleDateFormat.format(date);
        }
    }

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ThreadLocalDemo.returnDateStr(new Date());
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ThreadLocalDemo.returnDateStr(new Date());
            }
        }).start();
    }
}
