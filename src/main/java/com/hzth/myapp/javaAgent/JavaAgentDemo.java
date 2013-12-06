package com.hzth.myapp.javaAgent;

import java.lang.instrument.Instrumentation;

public class JavaAgentDemo {

	public static void premain(String agentArgs, Instrumentation inst) {
		System.out.println("before main");
		inst.addTransformer(new MonitorTransformer());
	}

}
