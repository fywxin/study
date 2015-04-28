package com.wjs.study.concurrent.countDownLatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 使用场景
 * 业务执行线程需要调用外部IO服务，业务线程对处理时间敏感，
 * 为防止调用外部IO线程时间太长导致业务线程挂死现象
 * 可以采用此方法来避免，前提是对外部IO
 * 
 * 用给定的计数 初始化 CountDownLatch。
 * 由于调用了 countDown() 方法，所以在当前计数到达零之前，await 方法会一直受阻塞。
 * 之后，会释放所有等待的线程，await 的所有后续调用都将立即返回。
 * 
 * 这种现象只出现一次——计数无法被重置。如果需要重置计数，请考虑使用 CyclicBarrier。 
 * 
 * CyclicBarrier. reset() 重置为其初始状态
 * 
 * @author 王金绍
 * 2015年4月28日 下午4:49:34
 */
public class MockFeture {

	public static void main(String[] args) {
		ReadRs readRs = new ReadRs();
		CountDownLatch latch = new CountDownLatch(1);
		new MockReadIo(latch, readRs).start();
		try {
			latch.await(2000L, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			System.out.println("处理超时，放弃线程执行，继续往下执行");
		}
		System.out.println("执行完成！"+readRs);
		
	}
	
	static class MockReadIo extends Thread {
		
		CountDownLatch latch;
		
		ReadRs readRs;
		
		public MockReadIo(CountDownLatch latch, ReadRs readRs){
			this.latch = latch;
			this.readRs = readRs;
		}

		@Override
		public void run() {
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
		}
		
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
