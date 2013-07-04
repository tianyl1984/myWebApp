package com.hzth.myapp.btrace;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnEvent;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.OnTimer;
import com.sun.btrace.annotations.Return;
import com.sun.btrace.annotations.Self;

@BTrace
public class TracingScript {

	@OnMethod(clazz = "com.hzth.myapp.btrace.Counter", method = "add", location = @Location(Kind.RETURN))
	public static void traceExecute(int num, @Return int result) {
		BTraceUtils.println(">----------add----------<");
		BTraceUtils.println(BTraceUtils.strcat("parameter num:", BTraceUtils.str(num)));
		BTraceUtils.println(BTraceUtils.strcat("return value:", BTraceUtils.str(result)));
		BTraceUtils.println(">----------add----------<");
	}

	@OnMethod(clazz = "com.hzth.myapp.btrace.Counter", method = "m1")
	public static void traceExecuteM1(Foo foo) {
		BTraceUtils.println(">---------m1-----------<");
		BTraceUtils.println(BTraceUtils.strcat("parameter:", BTraceUtils.str(BTraceUtils.get(BTraceUtils.field("com.hzth.myapp.btrace.Foo", "i"), foo))));
		BTraceUtils.println(">---------m1-----------<");
	}

	@OnMethod(clazz = "com.unitever.dc.ex.module.exam.service.ExamService", method = "delete", location = @Location(Kind.RETURN))
	public static void traceExecute(String num) {
		BTraceUtils.println(">--------------------<");
		BTraceUtils.println(BTraceUtils.strcat("parameter:", num));
		BTraceUtils.println(">--------------------<");
	}

	private static Object count;

	@OnMethod(clazz = "com.hzth.myapp.btrace.Foo", method = "<init>")
	public static void traceExecute1(@Self Foo count2) {
		count = count2;
	}

	@OnTimer(5000)
	public static void timerPrint() {
		BTraceUtils.println("<<<<<<<<timerPrint>>>>>>>");
		BTraceUtils.println(BTraceUtils.strcat("count:", BTraceUtils.str(BTraceUtils.get(BTraceUtils.field(BTraceUtils.classOf(count), "i"), count))));
	}

	@OnEvent("aaa")
	public static void eventA() {
		BTraceUtils.println("<<<<<<<<Event>>>>>>>");
		BTraceUtils.println(BTraceUtils.strcat("count:", BTraceUtils.str(BTraceUtils.get(BTraceUtils.field(BTraceUtils.classOf(count), "i"), count))));
	}

}
