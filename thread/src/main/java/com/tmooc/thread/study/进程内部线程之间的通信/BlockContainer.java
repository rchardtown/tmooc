package com.tmooc.thread.study.进程内部线程之间的通信;

/**
 * @author cuitao
 * @ className:BlockContainer
 * @ description:
 * 写一个基于消息队列为背景的模拟单一进程内部的不同线程之间的通信
 * 用得到的算法：FIFO
 * 项目消费者和生产者之间处理产品的日常行为案例
 * @ create 2021-03-21 13:39
 **/
public class BlockContainer {
    /**
     * 1.定义一个存储数据的数组，模拟生产者生产的产品
     * 2.定义生产的数据个数 size 以及数组的长度
     * 3.整体思路：生产者生产数据size++，消费者消费数据size--
     * 4.因为数组的长度是不可变的，所以当size>==Array.length时，生产行为阻塞，知道产品被消费者消费，即size<Array.length
     * 同理当size<=0 时，消费行为阻塞，直到生产者生产到数据 即 size>0
     */
    //1.定义一个存储数据的数组
    private Object[] array;
    //2.定义生产的数据个数 size
    private int size;

    //2.定义数组的长度
    public BlockContainer() {
        this(16);//无参构造中用this(参数列表)：表示调用使用了该参数列表类型和数据的有参构造方法
    }

    public BlockContainer(int cap) {
        array = new Object[cap];//此时的cap及时无参构造方法传递过来的16
    }

    //3.模拟生产者生产数据，生产的数据每次都放在size位置，当 size>=Array.length即16时，阻塞  ；另外，生产者每生产一个数据都通知消费者去消费数据
    public synchronized void put(Object t) {
        while (size >= array.length) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //生产数据
        array[size] = t;
        System.out.println("新生产的数据: "+t);
        size++;
        //通知消费者去消费数据： 采取激活消费者线程去争抢cpu 资源获取数据的技术来实现
        this.notifyAll();//为什么不怕激活的是别的线程而不是消费者这个线程，因为生产者和消费者用的是同一把锁，都是this,this这里指的是类实例，生产者激活别的
        //线程也只能激活消费者线程。包括上面的 this.wait 生产者阻塞了自己，释放了锁，用的是this.调用wait 能获取资源的也只能是具有同一把锁使用权限的消费者
    }

    //4.模拟消费者消费资源：基于FI-FO的原则，消费者每次只消费array[0]位置的数据，然后后面的数据整体前移以为
    public synchronized Object get() {
        while (size <= 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Object objct = array[0];
        System.out.println("新消费了数据: "+objct);
        //剩余数据整体前移以为
        System.arraycopy(array, 1, array, 0, size - 1);
        size--;
        array[size] = null;//不置空当然也是空的
        return objct;
    }
}
