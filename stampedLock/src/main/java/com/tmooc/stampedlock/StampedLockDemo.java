package com.tmooc.stampedlock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;

/**
 * @author cuitao
 * @ className:StampedLock案例
 * @ description:通过模拟一个容器，在这个容器中实现读写操作来联系StampedLock的读写操作
 * @ create 2021-04-10 11:32
 **/
public class StampedLockDemo {
    //创建一个容器
    private Map<String, Object> cache = new HashMap<>();
    //创建一个StampedLock对象
    private StampedLock stampedLock = new StampedLock();

    /**
     * 悲观读
     */
    public Object readObject(String key) {
        Object object = null;
        long stamp = stampedLock.readLock();
        //1、获取锁
        try {

            if (stamp < 0) {
                System.out.println("获取锁失败");
            }
            //2、读取数据
            else {
                object = cache.get(key);
            }

        } finally {
            //3、释放锁
            stampedLock.unlock(stamp);
            return object;
        }
    }

    /**
     * 写锁
     */
    public void writeLock(String key, Object object) {
        //1.获取写锁
        long stamp = stampedLock.writeLock();
        //2.写数据
        try {
            if (stamp < 0) {
                System.out.println("获取写锁失败");
            } else {
                cache.put(key, object);
                System.out.println("写入数据：" + key + "->" + object);
            }
        } finally {
            //3.释放锁
            stampedLock.unlock(stamp);
        }
    }

    /**
     * 乐观读（不加锁的读）
     */
    public Object readWithOptimisticRead(String key) {
        //1.尝试通过乐观读（不加锁的读）先获取数据 :备注：不加锁当然也不释放锁
        long stamp = stampedLock.tryOptimisticRead();
        Object object = cache.get(key);

        //2.验证乐观读的数据时是否有线程执行了写操作，如果有就要通过悲观读（加锁读）来重新读

        if (!stampedLock.validate(stamp)) {
            stamp = stampedLock.readLock();
            try {
                object = cache.get(key);
                System.out.println("悲观读取数据：" + key + "->" + object);
            } finally {
                //3.释放锁
                stampedLock.unlock(stamp);

            }
        }else{
            System.out.println("乐观读取数据：" + key + "->" + object);
        }
        return object;
    }

    public static void main(String[] args) {
        StampedLockDemo stampedLockDemo = new StampedLockDemo();
        stampedLockDemo.writeLock("A", 1);
        stampedLockDemo.writeLock("B", 2);
        Object object1 = stampedLockDemo.readWithOptimisticRead("A");
        stampedLockDemo.cache.clear();
        System.out.println(stampedLockDemo.cache);
        new Thread(() -> {
            int i=1;
            for(;;){
                stampedLockDemo.writeLock("A"+i,i);
                i++;
            }
        }).start();
//        new Thread(() -> {
//            int i=1;
//            for(;;){
//                stampedLockDemo.writeLock("B"+i,i);
//                i++;
//            }
//        }).start();
        new Thread(()->{
            int j=1;
            for(;;){
                stampedLockDemo.readWithOptimisticRead("A"+j);
                j++;
            }
        }).start();
        new Thread(()->{
            int j=1;
            for(;;){
                stampedLockDemo.readWithOptimisticRead("B"+j);
                j++;
            }
        }).start();
    }
}
