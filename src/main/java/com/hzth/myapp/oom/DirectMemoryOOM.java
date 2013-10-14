package com.hzth.myapp.oom;


/**
 * VM Args:-Xmx20M -XX:MaxDirectMemorySize=10M
 * 
 * 
 * @author tianyl
 * 
 *         本机直接内存溢出java.lang.OutOfMemoryError
 */
public class DirectMemoryOOM {

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		// 调用Unsafe类需要修改Preferences-〉Java-〉Compiler-〉Errors/Warnings-〉Deprecated and restricted API-〉Forbidden reference (access rules): 改成Warning
		// Field unsafeField = Unsafe.class.getDeclaredFields()[0];
		// unsafeField.setAccessible(true);
		// Unsafe unsafe = (Unsafe) unsafeField.get(null);
		// while (true) {
		// unsafe.allocateMemory(1024 * 1024);
		// }

	}
}
