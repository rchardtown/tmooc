package com.tmooc.readwritelock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author cuitao
 * @ className:ReadWriteLockDemo
 * @ description: 读写锁引用demo
 * @ create 2021-03-30 18:55
 **/

//构建一个简易的线程安全的MapCache对象，并允许多个线程同时从cache读数据。一个线程写数据。读写操作之间时互斥的

class MapCache {
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    Map<String, Object> mapCache = new HashMap<String, Object>();

    //允许多线程的读操作
    public Object readOperate(String key) {
        Object value = null;
        readWriteLock.readLock().lock();
        try {
            value = mapCache.get(key);
            TimeUnit.SECONDS.sleep(2);
            System.out.println(Thread.currentThread().getName() + "readOperate");
            return value;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
            System.out.println(Thread.currentThread().getName() + "readOperate  finally");
            return value;
        }
    }

    //只允许单个线程的写操作
    public void writeOpetate(String key, Object value) {
        readWriteLock.writeLock().lock();
        try {
            mapCache.put(key, value);
            System.out.println(Thread.currentThread().getName() + "writeOpetate");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            readWriteLock.writeLock().unlock();
            System.out.println(Thread.currentThread().getName() + "writeOpetate  finally");
        }
    }

}

public class ReadWriteLockDemo {

    public static void main(String[] args) {
        MapCache mapCache = new MapCache();
        new Thread(() -> mapCache.writeOpetate("A", 100)).start();
        new Thread(() -> mapCache.writeOpetate("B", 100)).start();
//        Thread-0writeOpetate
//        Thread-0writeOpetate  finally
//        Thread-1writeOpetate
//        Thread-1writeOpetate  finally
        new Thread(() -> mapCache.readOperate("A")).start();
        new Thread(() -> mapCache.readOperate("B")).start();
//
//        Thread-2readOperate
//        Thread-3readOperate
//        Thread-2readOperate  finally
//        Thread-3readOperate  finally

    }
}
