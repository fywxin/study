package com.wjs.study.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author wjs
 * @date 2020-03-03 14:48
 **/
public class Test4 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 100;
        });
        CompletableFuture<Void> f =  future.thenAccept(System.out::println);
        System.out.println("rs = "+f.get());

        CompletableFuture<Void> f3 =  future.thenRun(() -> System.out.println("f3 invoke"));
        System.out.println("rs3 = "+f3.get());

        CompletableFuture<Void> f2 =  future.thenAcceptBoth(CompletableFuture.completedFuture(10), (x, y) -> System.out.println(x * y));
        System.out.println("rs2 = "+f2.get());
    }
}
