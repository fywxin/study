package com.wjs.study.completableFuture;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * https://colobu.com/2016/02/29/Java-CompletableFuture/
 * JAVA8 CompletableFuture学习
 * @author wjs
 * @date 2020-03-03 14:16
 **/
public class CompletableFutureTest {
    public static CompletableFuture<Integer> compute() {
        final CompletableFuture<Integer> future = new CompletableFuture<>();
        return future;
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        final CompletableFuture<Integer> f = compute();
        class Client extends Thread {
            CompletableFuture<Integer> f;
            Client(String threadName, CompletableFuture<Integer> f) {
                super(threadName);
                this.f = f;
            }
            @Override
            public void run() {
                try {
                    //取值
                    System.out.println(this.getName() + ": " + f.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
        new Client("Client1", f).start();
        new Client("Client2", f).start();
        System.out.println("waiting");
        Thread.sleep(1000);
        System.out.println("finish");
        //设置值，返回结果
        f.complete(100);
        //处理完成，结果异常，设置异常
        //f.completeExceptionally(new Exception());



        System.in.read();


        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            int i = 1/0;
            return 100;
        });
        System.out.println(future.get());
        System.out.println(future.join());
    }
}
