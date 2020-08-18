package com.wjs.study.jdk8;

/**
 * https://xwjie.github.io/webflux/webflux-study-path.html#lambda%E8%A1%A8%E8%BE%BE%E5%BC%8F%E4%B8%AD%E7%9A%84this
 * @author wjs
 * @date 2020-03-04 18:26
 **/
public class ThisDemo {

    private String name = "ThisDemo";

    public void test() {
        // 匿名类实现
        new Thread(new Runnable() {

            private String name = "Runnable";

            @Override
            public void run() {
                System.out.println("这里的this指向匿名类:" + this.name);
            }
        }).start();

        // lambda实现
        new Thread(() -> {
            System.out.println("这里的this指向当前的ThisDemo类:" + this.name);
        }).start();
    }

    public static void main(String[] args) {
        ThisDemo demo = new ThisDemo();
        demo.test();
    }
}
