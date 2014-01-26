package com.hzth.myapp.dc;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Before;

//@Aspect
//@Component
public class TransCheckAspect {

	@AfterReturning("execution(* com.unitever.dc.ex.module.*.dao.*.*(..))")
	public void after(JoinPoint joinPoint) {
		// System.out.println("after");
		// printLog("after");
	}

	@Before("execution(* com.unitever.dc.ex.module.*.dao.*.*(..))")
	public void before(JoinPoint joinPoint) {
		// System.out.println("before");
		// printLog("before");
	}

	private void printLog(String when) {
		try {
			// System.out.println("0");
			// MyAbstractRoutingDataSource ds = SpringContextHolder.getBean("dataSource");
			// System.out.println("1");
			// int tran = ds.getConnection().getTransactionIsolation();
			// System.out.println("2");
			// SimpleJdbcTemplate template = SpringContextHolder.getBeanOneOfType(SimpleJdbcTemplate.class);
			// System.out.println("3");
			// NamedParameterJdbcTemplate npjt = (NamedParameterJdbcTemplate) template.getNamedParameterJdbcOperations();
			// System.out.println("4");
			// JdbcTemplate aa = (JdbcTemplate) npjt.getJdbcOperations();
			// System.out.println("5");
			// // int tran2 = aa.getDataSource().getConnection().getTransactionIsolation();
			// int tran2 = 2;
			// System.out.println(when + ":" + tran + "--" + tran2);
			// if (tran - Connection.TRANSACTION_READ_COMMITTED != 0 || tran2 - Connection.TRANSACTION_READ_COMMITTED != 0) {
			// System.out.println("----------------------------异常---------------------");
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
