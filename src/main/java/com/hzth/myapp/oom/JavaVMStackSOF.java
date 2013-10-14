package com.hzth.myapp.oom;

/**
 * VM Args:-Xss128k
 * 
 * @author tianyl 线程请求的栈深度大于虚拟机所允许的最大深度
 * 
 *         虚拟机栈和本地方法栈溢出 java.lang.StackOverflowError
 */
public class JavaVMStackSOF {
	private int stackLength = 1;

	private void stackLeak() {
		stackLength++;
		System.out.println(stackLength);
		stackLeak();
	}

	public static void main(String[] args) {
		JavaVMStackSOF oom = new JavaVMStackSOF();
		oom.stackLeak();
	}
}
