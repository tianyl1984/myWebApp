package com.hzth.myapp.javaAgent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

public class MonitorTransformer implements ClassFileTransformer {

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		if (className.startsWith("com/hzth/myapp/javaAgent")) {
			className = className.replaceAll("/", ".");
			try {
				CtClass ctClass = ClassPool.getDefault().get(className);
				CtMethod[] ctMethods = ctClass.getMethods();
				for (CtMethod method : ctMethods) {
					String methodName = method.getName();
					String newMethodName = methodName + "$impl";
					if (method.getName().equals("startTest")) {
						method.setName(newMethodName);// 修改原来的方法名
						CtMethod newMethod = CtNewMethod.copy(method, methodName, ctClass, null);
						StringBuffer sb = new StringBuffer();
						sb.append("{\n");
						sb.append("long startTime = System.currentTimeMillis();\n");
						sb.append(newMethodName + "($$);\n");// 执行原方法
						sb.append("long endTime = System.currentTimeMillis();\n");
						sb.append("System.out.println(\"共用时：\" + (endTime - startTime));");
						sb.append("}");
						newMethod.setBody(sb.toString());
						ctClass.addMethod(newMethod);
					}
				}
				return ctClass.toBytecode();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
