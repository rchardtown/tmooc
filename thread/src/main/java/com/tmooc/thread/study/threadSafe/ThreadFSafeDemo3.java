package com.tmooc.thread.study.threadSafe;

import java.util.Arrays;

/**
 * @author cuitao
 * @ className:ThreadFSafeDemo3
 * @ description: 写一个线程安全的容器
 * @ create 2021-03-06 17:01
 **/
public class ThreadFSafeDemo3 {
    /**
     * 这是一个线性安全的容器，在这个类中学习的点是，加锁的对象，如果对同一个对象可能同时进行读操作，写操作
     * 这个时候要保证线性安全，就要读写时都加锁，    此时   加锁的对象一定要唯一，也即是对同一个对象加锁
     */
    static class Container {
        private int size;
        private int[] array;

        public Container(int size) {
            array = new int[size];
        }

        public Container() {
            array = new int[16];
        }

        void add(int obj) {
            synchronized (this) { //对 this 加锁
                if (size >= array.length) {
                    array = Arrays.copyOf(array, 2 * array.length);
                }
                array[size] = obj;
                size++;
            }
        }

        synchronized int get() {  //对 this加锁
            int value = array[0];
            return value;
        }

        synchronized int getSize() {  // 对this 加锁
            return size;
        }
    }
}
