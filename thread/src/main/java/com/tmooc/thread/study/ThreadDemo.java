package com.tmooc.thread.study;

/**
 * @author cuitao
 * @ className:ThreadDemo
 * @ description: 线程详细讲解
 * @ create 2021-03-06 10:42
 **/
public class ThreadDemo {
    /**
     * 首先，任何进程中都有默认线程，即主线程，当我们new 一个线程时，实际上是在创建另外个线程，可以称其为子线程。
     * 现在子线程也创建了，那么问题来了：
     *      新建的线程，和 主线程 谁先执行？  [进程中同一时间点只能执行一个线程]
     * 为了解决这个问题，引入了：
     *      Thread.sleep  ：让主线程阻塞，子线程先运行
     *      子线程.join  : 子线程先运行，运行结束后，主线程再运行
     *
     */
   static  String content ;
    public static void main(String[] args) throws InterruptedException {
        /**
         *
         * 主线程运行
         */

        //新建线程：一般称其为子线程
        Thread thread = new Thread();
        //线程中添加任务，不然建线程没意义
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //这里给线程添加任务，后续如果要执行某一个任务，也可以创建一个任务对象，让它实现Runbale ，因为底层都是运行run()方法
                content="==============给线程添加任务==================";
                System.out.println(content);
            }
        });
        //激活线程，使其处于就绪状态
        t.start();
        /**
         * 就绪状态的线程是否能够运行，取决于是否能活到对应进程中的资源：
         *      如果资源搞好到位，立马运行线程中的任务，此时线程处于运行状态-->线程中的任务运行结束后，线程结束，处于死亡状态
         *
         *      如果进程中资源被占用，就要等待，就会报空指针
         */
        // Thread.sleep(200);【阻塞】主线程，让子线程线运行
        //t.join(); 让子线先运行，直到子线程运行结束后，主线程才会继续执行。问题是子线程到底执行多久才能执行结束，要考虑一下
       while(content==null){//空旋
           Thread.yield();//让当前线程[这里是主线程]放弃运行，处于【就绪】状态-->这样其他线程就有了获取资源的机会
                            //使用yield 好处，既能放弃当前线程，让别的线程运行，又不会让自己处于阻塞状态，处于就绪状态的自己后面
                            //还可以和别的线程抢资源后运行
       }
        System.out.println("继续运行主线程"+content.toUpperCase());
    }
}
