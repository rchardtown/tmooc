package com.tmooc.thread.study.deadThread;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author cuitao
 * @ className:DeadThreadDemo
 * @ description:死锁
 * <p>
 * 锁中调锁，单线程下是可行的，多线程就会形成死多
 * @ create 2021-03-20 10:46
 **/

@Slf4j
public class DeadThreadDemo implements Runnable {
    private List<Integer> source;
    private List<Integer> target;
    private Integer item;

    public DeadThreadDemo(List<Integer> source, List<Integer> target, Integer item) {
        this.source = source;
        this.target = target;
        this.item = item;
    }

    @SneakyThrows
    @Override
    public void run() {
        moveListItem(source, target, item);

    }

    static void moveListItem(List<Integer> source, List<Integer> target, Integer item) throws InterruptedException {
        //因为ArrayList是线性不安全的，所以多线程调用时要加锁

        synchronized (source) {
            printLog("加第一把鎖", source);
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (UnsupportedOperationException e) {
                e.printStackTrace();
            }

            synchronized (target) {
                {
                    printLog("加锁中锁", target);
                    target.add(item);
                    TimeUnit.SECONDS.sleep(10);
                }
            }


        }

    }

    private static void printLog(String msg, Object object) {
        log.info("threadName:{} msg:{} objecctHashCode:{}", Thread.currentThread().getName(), msg, System.identityHashCode(object));
    }

    public static void main(String[] args) {
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(4, 5, 6);
        //多线程调用锁中锁的程序
        new Thread(new DeadThreadDemo(list1, list2, 1)).start();
        new Thread(new DeadThreadDemo(list2, list1, 4)).start();
//        - threadName:Thread-0 msg:加第一把锁 objecctHashCode:545882046
//        - threadName:Thread-1 msg:加第一把锁 objecctHashCode:710985493
    }
}
