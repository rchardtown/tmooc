package com.tmooc.thread.study;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author cuitao
 * @ className:CreateThread
 * @ description:创建线程
 * @ create 2021-03-13 11:59
 **/
public class CreateThread {
}

//继承Thread
class ExtendThread extends Thread {
    //重写父类Thread的run方法，以编写要写的执行体
    @Override
    public void run() {
        //super.run();
        System.out.println("执行体运行，当前线程名：" + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        ExtendThread extendThread = new ExtendThread();
        ExtendThread extendThread2 = new ExtendThread();
        //启动后，线程自动去找执行体执行
        extendThread.start();
        extendThread2.start();
    }

}

//实现Runable接口
class RunableImpl implements Runnable {

    @Override
    public void run() {
        System.out.println("执行体开始运行，当前线程优先级：" + Thread.currentThread().getPriority() + "  线程名称：" + Thread.currentThread().getName());
    }

    public static void main(String[] args) throws InterruptedException {
        RunableImpl runableImpl = new RunableImpl();
        //将 RunableImpl 实例对象 runableImpl 作为参数传到 Thread 实例对象中
        new Thread(runableImpl, "自定义线程名称；线程1").start();
        new Thread(runableImpl, "自定义线程名称；线程2").start();
        Thread.currentThread().isInterrupted();
        Thread.currentThread().setPriority(10);
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        condition.await();
        condition.signal();
        condition.signalAll();
    }


}