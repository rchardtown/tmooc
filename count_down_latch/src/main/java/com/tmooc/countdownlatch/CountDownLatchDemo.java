package com.tmooc.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @author cuitao
 * @ className:CountDownLatchDemo
 * @ description:需求：   主线程依赖子线程给content赋值后，再去读取它并转换为大写
 * @ create 2021-04-10 14:45
 **/
public class CountDownLatchDemo {
    static String content;

    public static void main(String[] args) {

        //创建CountDownLatch 对象并赋初始值
        CountDownLatch countDownLatch = new CountDownLatch(1);
        //创建子线程
        new Thread(() -> {
            content = "hello word";
            //每调用一次 countDown() 方法，计数值就会减1
            countDownLatch.countDown();
            while(true);

        }).start();
        try {
            //线程可以调用CountDownLatch的await方法进入阻塞，当计数值降到0时，所有之前调用await阻塞的线程会自动被激活。
            countDownLatch.await();
            //当主线程被激活(业务角度：content==0)时，向下继续执行
            content=content.toUpperCase();
            System.out.println("content = "+content);
            //可以看到主线程不用等到子线程走完后再运行，只需要等到给 content 赋值结束就可以继续走
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
