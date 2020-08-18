package com.wjs.study.jdk8;

import java.util.function.IntUnaryOperator;

/**
 * @author wjs
 * @date 2020-03-04 18:38
 **/
public class MethodRefrenceDemo {

    public static void main(String[] args) {
        // 静态方法的方法引用
        IntUnaryOperator methodRefrence1 = DemoClass::staticMethod;
        System.out.println(methodRefrence1.applyAsInt(111));

        DemoClass demo = new DemoClass();
        System.out.println(demo);

        // 实例方法的方法引用
        IntUnaryOperator methodRefrence2 = demo::normalMethod;
        System.out.println(methodRefrence2.applyAsInt(111));
    }
}


class DemoClass {

    /**
     * 这里是一个静态方法
     */
    public static int staticMethod(int i) {
        return i * 2;
    }

    /**
     * 这里是一个实例方法
     */
    public int normalMethod(int i) {
        System.out.println("实例方法可以访问this:" + this);
        return i * 3;
    }

}