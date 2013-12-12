package com.hzth.myapp.cache;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CacheAspect {

	public static final Map<String, Object> cacheMap = new HashMap<String, Object>();

	@Around("execution(* com.unitever.dc.ex.module.score.dao.ScoreCountDAO.*(..)) ")
	public Object beforeCache(ProceedingJoinPoint pjp) throws Throwable {
		String key = getKey(pjp);
		System.out.println(key);
		System.out.println("before");
		// if (cacheMap.containsKey(key)) {
		// return cacheMap.get(key);
		// }
		Object obj = pjp.proceed();
		if (obj != null) {
			if (obj instanceof Collection) {
				Collection<Object> coll = (Collection<Object>) obj;
				if (!coll.isEmpty()) {
					Object ele = coll.iterator().next();
					System.out.println(ele.getClass());
					// JsonUtil.json2List("", ele.getClass());
				}
			}
		}
		// cacheMap.put(key, obj);
		System.out.println("after");
		return obj;
	}

	// 需要修改
	private String getKey(ProceedingJoinPoint pjp) {
		Object args[] = pjp.getArgs();
		String methodName = pjp.getSignature().getName();
		String className = pjp.getTarget().getClass().getName();
		String key = className + methodName;
		for (Object obj : args) {
			if (obj != null) {
				key += obj.toString();
			}
		}
		return key;
	}
}
