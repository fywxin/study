package com.wjs.study.jdk8;

/**
 * https://xwjie.github.io/webflux/webflux-study-path.html#lambda%E5%AE%9E%E4%BE%8B%E6%96%B9%E6%B3%95%E7%9A%84%E6%96%B9%E6%B3%95%E5%BC%95%E7%94%A8
 * @author wjs
 * @date 2020-03-04 18:48
 **/
public class MethodRefDemo2 {

    public static void main(String[] args) {
        DemoClass2 demo2 = new DemoClass2();

        // 代码定义上有2个参数, 第一个参数为this
        // 但实际上调用的时候只需要一个参数
        System.out.println(demo2.normalMethod(1));
    }
}

class DemoClass2{

    /**
     * 这里是一个实例方法, 代码上2个参数
     * 而我们调用的时候只有一个参数
     */
    public int normalMethod(DemoClass2 this,int i) {
        return i * 2;
    }
}
