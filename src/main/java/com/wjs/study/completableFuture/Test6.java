package com.wjs.study.completableFuture;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author wjs
 * @date 2020-03-03 15:04
 **/
public class Test6 {

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        future.complete(1);
        System.out.println("1");

        future.get();
        System.out.println(2);
        System.in.read();
    }
}
