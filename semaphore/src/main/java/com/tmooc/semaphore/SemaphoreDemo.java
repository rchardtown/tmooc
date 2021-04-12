package com.tmooc.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author cuitao
 * @ className:SemaphoreDemo
 * @ description:限流处理
 * @ create 2021-04-10 16:41
 **/

public class SemaphoreDemo {
    private Semaphore permit = new Semaphore(5, true); // 5：证书个数， true：公平锁，fasle:非公平锁
    //要限流的服务或者接口
    void doServer() {
        try {
           String threadName= Thread.currentThread().getName();
            //获取许可证 :会一次性将所有的许可证都发完后再一起 执行后面的程序，即一批一批发证书，同一批次内的人都拿到证书后才能执行任务
            permit.acquire();
            //获取到许可证的用户在执行任务
            System.out.println("获取到许可证的用户在执行任务"+threadName);
            TimeUnit.SECONDS.sleep(2);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //释放许可证
            permit.release();


        }

    }

    public static void main(String[] args) {
        /**dfd
         * 【程序入口】
         * 业务背景：
         *      每次只允许5个用户访问某个接口，多出来的人阻塞，直到前面5个人访问结束后才能访问
         * 技术保证措施：
         *      只准备5个许可证，每次访问前先发许可证，发完就不发了。没有许可证不允许访问。访问技术的还回许可证，保证后面的人有许可证可以拿到
         */
        SemaphoreDemo  semaphoreDemo = new SemaphoreDemo();
        for (int i = 0; i < 10 ; i++) {
            new Thread(()->semaphoreDemo.doServer()).start();
        }
        System.out.println("我是分支2");
    }
}
