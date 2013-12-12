package com.hzth.myapp.javaAgent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;

public class AgentmainDemo {

	public static void agentmain(String agentArgs, Instrumentation inst) {
		System.out.println("-->agentmain<--");
		inst.addTransformer(new ClassFileTransformer() {
			@Override
			public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
				System.out.println("in agentmain transform");
				return null;
			}
		});
		try {
			inst.retransformClasses(JavaAgentTest.class);
		} catch (UnmodifiableClassException e) {
			e.printStackTrace();
		}
	}

}
