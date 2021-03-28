package com.tmooc.interfacedefaultandstaticmethod;

/**
 * @author cuitao
 * @ className:IntefaceDefaultMethod
 * @ description:
 * 接口默认方法，可以有0个，多个，而默认方法的使用，使得接口的方法增加，不用考虑到接口实现类的大面积适配，或者不用在写钩子方法
 * 接口实现类，只需要去选择性的重写接口中的默认方法，包括新增的默认方法即可
 *
 * @ create 2021-02-27 10:45
 **/


public class IntefaceDefaultMethod {

//
//    public static void main(String[] args) {
//
//
//        IAimpl iAimpl = new IAimpl();
//        //调用接口实现类中重写接口后的方法
//        iAimpl.defaultmethod1();
//    }
}

/**
 * 接口中定义默认方法，子类可选择性的重写
 */
interface IA {
    default void defaultmethod1() {
        System.out.println("接口中有了默认方法，子类可选择性重写");
    }

    default void defaultMethod2() {
    }

    //抽象方法
    void abstractMethod();
}

/**
 * 接口实现类，可以不重写，接口默认方法，可以重写一部分接口默认方法，但是必须实现口的抽象方法
 */
class IAimpl implements IA {
    //实现接口的抽象方法
    @Override
    public void abstractMethod() {
        System.out.println("实现接口中的抽象方法");
    }

    //选择性的重写接口的默认方法
    @Override
    public void defaultMethod2() {
        System.out.println("选择性的从写接口默认方法");
    }

    //重写的接口方法，可以选择是调用接口的默认方法，还是重写该方法
    public void defaultmethod1() {
        //调用接口的原方法
        IA.super.defaultmethod1();
        //重写接口的该方法
        System.out.println("重写接口的默认方法");
    }
}


