package com.tmooc.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @author cuitao
 * @ className:SyclicBarrierDemo
 * @ description: 循环栅栏举例
 * @ create 2021-04-10 15:40
 **/
public class CyclicBarrierDemo {
    /**
     * 业务背景：
     * 一批人进行一项任务，这个任务分好几个阶段，特别要求所有人都完成第一个阶段后，才能一起开始第二个阶段的任务
     */

    //定义CyclicBarrier 对象，指定一起完成任务的人数，和所有人都完成第一阶段任务的标识
    static CyclicBarrier cyclicBarrier = new CyclicBarrier(10, new Runnable() {
        @Override
        public void run() {
            //这里边定义第一阶段任务完成的标识，也只有所有的人(这里指 parties:10个人)完成第一阶段任务后，这个方法才会执行
            System.out.println("=============所有人完成第一阶段任务=================");
        }
    });

    //要完成的任务
    static class WorkTask implements Runnable {

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            //任务第一个阶段
            taskStepOne(threadName);
            //通过SycLicBarrier来确保所有人都完成了第一阶段后，再一起开始第二阶段的任务
            try {
                cyclicBarrier.await();//等待所有的人都执行完taskStepOne任务后，程序再继续向下执行
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            //任务第二个阶段
            taskStepTwo(threadName);
        }

        //任务第一个阶段
        void taskStepOne(String threadName) {
            System.out.println("线程 " + threadName + "完成第一阶段");
        }

        //任务第一个阶段
        void taskStepTwo(String threadName) {
            System.out.println("线程 " + threadName + "完成第二阶段");
        }
    }

    public static void main(String[] args) throws InterruptedException {

        /**
         * 【程序入口】
         */
        //创建要完成的任务对象
        WorkTask workTask = new WorkTask();
        //模拟多人去完成任务，这里用多每个线程代表一个人
        for (int i = 0; i < 10; i++) {
            new Thread(workTask).start();
        }

        TimeUnit.SECONDS.sleep(2);
        //重置到设置好的初始化状态
        cyclicBarrier.reset();
        System.out.println("=========================第二波人接着可以继续上面的操作[支持循环]=============================");
        for(int i=0;i<10;i++) {
            new Thread(workTask).start();
        }
    }
}
