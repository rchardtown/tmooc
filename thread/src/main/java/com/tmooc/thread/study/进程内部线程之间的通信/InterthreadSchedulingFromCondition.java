package com.tmooc.thread.study.进程内部线程之间的通信;

import java.util.concurrent.TimeUnit;

/**
 * @author cuitao
 * @ className:InterthreadScheduling
 * @ description:
 * 单一进程内部线程之间调度的demo
 * @ create 2021-03-21 14:39
 **/
public class InterthreadSchedulingFromCondition {

    /**
     * 1.创建一个或多个生产者线程
     * 2.创建一个或者多个消费者线程
     * 3.通过主线程调用生产线程池生产任务，同时调用消费线程池消费
     */

    //1.创建生产者线程
    public static class ProductTask implements Runnable {


        //创建生产线程要工作要用的 BlockContainer，同时将其初始化[至于 BlockContainer容器的初始值大小，一般是在主线程中统一定义]
        private BlockContainerFromCondition blockContainerFromCondition;

        public ProductTask(BlockContainerFromCondition blockContainer) {
            this.blockContainerFromCondition = blockContainer;
        }

        @Override
        public void run() {
            int i = 0;
            while (true) {
                this.blockContainerFromCondition.put(i);
                i++;
                sleep();
            }

        }

        /**
         * 模拟工作任务耗时
         */
        private void sleep() {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //2.创建消费者线程
    static class ConsmerTask extends Thread {
        private BlockContainerFromCondition blockContainerFromCondition;

        public ConsmerTask(BlockContainerFromCondition blockContainerFromCondition) {
            this.blockContainerFromCondition = blockContainerFromCondition;
        }

        @Override
        public void run() {
            while (true) {
                this.blockContainerFromCondition.get();
                sleep();
            }
        }

        private void sleep() {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // 1、创建同一个对象，供生产者生产，消费者消费
        BlockContainerFromCondition blockContainerFromCondition = new BlockContainerFromCondition(20);
        //2.创建多个生产线程
        Thread proThread1 = new Thread(new ProductTask(blockContainerFromCondition));
        Thread proThread2 = new Thread(new ProductTask(blockContainerFromCondition));
        proThread1.start();
        proThread2.start();
        //3.创建多个消费线程
        Thread cosThread1 = new Thread(new ConsmerTask(blockContainerFromCondition));
        Thread cosThread2 = new Thread(new ConsmerTask(blockContainerFromCondition));
        cosThread1.start();
        cosThread2.start();
    }
}
