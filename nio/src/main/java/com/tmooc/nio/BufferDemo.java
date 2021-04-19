package com.tmooc.nio;

import java.nio.ByteBuffer;
import java.util.function.DoublePredicate;

/**
 * @author cuitao
 * @ className:
 * @ description:
 * @ create 2021-04-18 16:40
 **/
public class BufferDemo {
    public static void main(String[] args) {
        //Buffer创建的几个途径
        //途径1 :创建在操作系统中的缓冲
        ByteBuffer osBuffer = ByteBuffer.allocateDirect(1024);
        //途径2 :创建在JVM中的缓冲
        ByteBuffer jvmBuffer = ByteBuffer.allocate(1024);
        doPrint(jvmBuffer.position(), jvmBuffer.limit(), jvmBuffer.capacity());
        //写数据
        jvmBuffer.put("hello word".getBytes());
        doPrint(jvmBuffer.position(), jvmBuffer.limit(), jvmBuffer.capacity());
        //转换读写模式
        jvmBuffer.flip();
        //读数据
        char b = (char)jvmBuffer.get();
        System.out.println(b);
        doPrint(jvmBuffer.position(), jvmBuffer.limit(), jvmBuffer.capacity());
        //清空读取过的数据
        jvmBuffer.compact();
        jvmBuffer.put("jiayouo".getBytes());
        doPrint(jvmBuffer.position(), jvmBuffer.limit(), jvmBuffer.capacity());
    }

    static void doPrint(int position, int limit, int capacity) {
        System.out.println("position:" + position);
        System.out.println("limit:" + limit);
        System.out.println("capacity:" + capacity);
    }

}
