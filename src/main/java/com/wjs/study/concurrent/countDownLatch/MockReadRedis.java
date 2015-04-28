package com.wjs.study.concurrent.countDownLatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch 发挥作用，必须是在主线程上创建一个子线程，主线程有限制等待子线程处理结果
 * 
 * 如下想法不可行
 * 
 * @author 王金绍
 * 2015年4月28日 下午5:41:06
 */
public class MockReadRedis {

	public static void main(String[] args) {
		ReadRs readRs = new ReadRs();
		CountDownLatch latch = new CountDownLatch(1);
		//callRedis(latch, readRs);
		try {
			latch.await(12000L, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			System.out.println("处理超时，放弃线程执行，继续往下执行");
		}
		callRedis(latch, readRs);
		System.out.println("执行完成！"+readRs);
		
	}
	
	public static void callRedis(CountDownLatch latch, ReadRs readRs){
		System.out.println("开始请求网络");
		Random random = new Random();
		Integer time = random.nextInt(5000);
		if(time < 0){
			time = -time;
		}
		try {
			System.out.println(time);
			Thread.sleep(time);
		} catch (InterruptedException e) {
			
		}
		readRs.setCode(time);
		System.out.println("处理完成");
		latch.countDown();
	}
	
	static class ReadRs{
		private Integer code;

		public Integer getCode() {
			return code;
		}

		public void setCode(Integer code) {
			this.code = code;
		}

		@Override
		public String toString() {
			return "ReadRs [code=" + code + "]";
		}
	}
}
