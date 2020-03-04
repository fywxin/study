package com.wjs.study.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author wjs
 * @date 2020-03-03 15:12
 **/
public class Test7 {

    public static void main(String[] args) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            int i=0;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            int j = 1/i;

            return "2";
        });
        try {
            System.out.println(future.isCompletedExceptionally());
            System.out.println(future.get());
            System.out.println(future.get());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(future.isCompletedExceptionally());
    }
}
