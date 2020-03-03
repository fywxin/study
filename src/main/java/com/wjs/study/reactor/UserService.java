package com.wjs.study.reactor;

import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

/**
 * @author wjs
 * @date 2020-03-03 11:52
 **/
public class UserService {

    public Flux<Integer> getFav(Integer userId){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Flux.fromStream(Arrays.asList(1,3,4,5,6).stream());
    }


    public static void main(String[] args) {
        UserService userService = new UserService();
        userService.getFav(1);
    }
}
