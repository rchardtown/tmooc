package com.tmooc.interfacedefaultandstaticmethod;

/**
 * @author cuitao
 * @ className:IntefaceStaticMethod
 * @ description:接口静态方法
 * 1.方法的重写标志就是@overWrite ，如果没有这个注解，表明是相同方法名的两个独立的方法
 * 2.静态方法不能被重写
 * 3.静态方法只能用接口名||类名直接调用
 * 4.方法调用时，看的是 =左边，左边是接口，就是用接口调用，左边是接口实现类就是用接口实现类来调用
 * @ create 2021-02-27 11:22
 **/
public class IntefaceStaticMethod {
    public static void main(String[] args) {
        //调用接口静态方法
        IStatic.doPrint();
        //调用接口实现类中的方法
        IStaticImpl.doPrint();
    }
}

interface IStatic {
    //定义一个接口静态方法
    public static void doPrint() {
        System.out.println("接口静态方法");
    }
}

class IStaticImpl implements IStatic {
    //因为没有@overWrite--> 单独的一个和原先的接口方法doPrint 无关，只是恰好名字相同的方法
    //当然，该方法也不能写 @overWrite,因为凡是静态方法都不能被重写
    static void doPrint() {
        System.out.println("单独的方法");
    }
}