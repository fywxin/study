package com.wjs.study.completableFuture;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * https://juejin.im/post/5adbf8226fb9a07aac240a67
 *
 * 在thenApply()中的任务和在supplyAsync()中的任务执行在相同的线程中。任何supplyAsync()立即执行完成,那就是执行在主线程中（尝试删除sleep测试下）。
 * 为了控制执行回调任务的线程，你可以使用异步回调。如果你使用thenApplyAsync()回调，将从ForkJoinPool.commonPool()获取不同的线程执行。
 *
 * @author wjs
 * @date 2020-03-03 14:40
 **/
public class Test3 {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 100;
        });
        CompletableFuture<String> f =  future.thenApply(i -> {//主线程执行
            System.out.println("thenApply1");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thenApply1 - ok - "+Thread.currentThread().getName());
            return i+1;
        }).thenApplyAsync(i -> { //使用 ForkJoinPool.commonPool() 的线程执行
            System.out.println("thenApplyAsync");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thenApplyAsync - ok - "+Thread.currentThread().getName());
            return i * 10;
        }).thenApply(i -> {
            System.out.println("thenApply - "+Thread.currentThread().getName());
            return i.toString();
        });
        System.out.println(f.get());
        System.in.read();
    }
}
