package com.tmooc.thread.study.threadSafe;

/**
 * @author cuitao
 * @ className:ThreadSafe
 * @ description:线程安全
 * 案例实现： 多线程售票任务，考察线程安全问题
 * @ create 2021-03-06 11:48
 **/


public class ThreadSafeDemo1 {

    //创建一个线程，在线程里边运行一个售票任务
    public static class TicketTask implements Runnable {
        int ticketNum = 10;

        @Override
        public void run() {
            //线程中运行售票任务
            doTicket();
        }

        public void doTicket() {  //这里加锁，相当于给整个售票任务加锁，结果是只允许一个线程进来调度,不推荐

            while (true) {
                //多个线程在此代码块上【排队】进行，即多个线程同步执行。彼此之间抢CPU
                synchronized (this) { //这里加this锁，相当于对每个售票动作加锁，每次售票动作之间不影响
                                      //分析： doTicket方法被run()方法调用，run方法属于TicketTask，而我们的调用中TicketTask 只有唯一的
                                      //        实例对象，满足锁的条件  ：唯一性 要求
                    if (ticketNum <= 0) {
                        break;
                    }
                    String treadName= Thread.currentThread().getName();
                    System.out.println(treadName+": "+ticketNum--);
                    sleep();
                }//synchronized 锁中有一个监视器，只允许一个调度进来，在锁住的内容没有执行完前，其他调度不允许进来
                //  这种锁称之为：  排它锁（独占锁）
            }
        }


        //模拟卖票时间
        void sleep() {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        //查询电脑中的cpu 数量，也是最大并行数量
        Runtime runtime = Runtime.getRuntime();
        int cpuCount = runtime.availableProcessors();
        System.out.println("cpuCount: " + cpuCount);

        //1.创建售票任务
        TicketTask ticketTask = new TicketTask();
        //2.模拟多个线程同时售票
        Thread threa1 = new Thread(ticketTask);//thread(要么写匿名内部类,实现run()方法，要么放任务对象，任务对象底层也是实现的run()方法)
        Thread thread2 = new Thread(ticketTask);
        Thread thread3 = new Thread(ticketTask);
        Thread thread4 = new Thread(ticketTask);
        //3.同时运行多个线程
        threa1.start();
        thread2.start();
        thread3.start();
        thread4.start();

    }

}
