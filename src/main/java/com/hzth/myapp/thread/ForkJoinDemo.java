package com.hzth.myapp.thread;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 * ForkJoin框架简单使用
 * 
 * @author tianyl
 * 
 */
public class ForkJoinDemo {

	public static void main(String[] args) throws Exception {
		ForkJoinPool pool = new ForkJoinPool();
		ForkJoinTask<Integer> task = new CountTask(1, 500);
		pool.submit(task);
		// 关闭任务池禁止添加任务
		pool.shutdown();
		// 等待任务结束
		pool.awaitTermination(10, TimeUnit.DAYS);

		// 判断任务状态
		if (task.isCompletedAbnormally()) {// 判断是否正常退出
			System.out.println("CompletedAbnormally");
			Throwable ex = task.getException();
			if (ex != null) {
				System.out.println(ex.getMessage());
			}
		} else {
			Integer result = task.get();
			System.out.println("结果：" + result);
		}
		System.out.println("退出。。。");
	}

}

class CountTask extends RecursiveTask<Integer> {

	private static final long serialVersionUID = 1160071264161942356L;

	private Integer from;

	private Integer to;

	private static final Integer THRESHOLD = 20;

	public CountTask(Integer from, Integer to) {
		this.from = from;
		this.to = to;
	}

	@Override
	protected Integer compute() {
		if (to - from > THRESHOLD) {
			System.out.println("分解任务：" + from + " " + to);
			// 分解任务
			int mid = (to - from) / 2;
			CountTask ctLeft = new CountTask(from, from + mid);
			CountTask ctRight = new CountTask(from + mid + 1, to);
			// 执行任务
			ctLeft.fork();
			ctRight.fork();

			// 等待子任务完成
			int leftSum = ctLeft.join();
			int rightSum = ctRight.join();
			return leftSum + rightSum;
		} else {
			System.out.println("计算任务：" + from + " " + to);
			return doComputer();
		}
	}

	private Integer doComputer() {
		if (to > 600) {
			System.out.println("出现异常！！");
			throw new RuntimeException("number is to big!");
		}
		System.out.println(String.format("执行：Thread:%s,From:%d,To:%d", Thread.currentThread().getName(), from, to));
		Integer sum = 0;
		for (int i = from; i <= to; i++) {
			sum += i;
		}
		return sum;
	}

}