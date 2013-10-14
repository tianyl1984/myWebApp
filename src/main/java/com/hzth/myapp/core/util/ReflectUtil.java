package com.hzth.myapp.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.springframework.util.Assert;

import com.sun.org.apache.commons.beanutils.ConvertUtils;

public class ReflectUtil {
	/**
	 * 调用Getter方法.
	 */
	public static Object invokeGetterMethod(Object target, String propertyName) {
		String getterMethodName = "get" + StringUtils.capitalize(propertyName);
		return invokeMethod(target, getterMethodName, new Class[] {}, new Object[] {});
	}

	/**
	 * 直接调用对象方法, 无视private/protected修饰符.
	 */
	public static Object invokeMethod(final Object object, final String methodName, final Class<?>[] parameterTypes,
			final Object[] parameters) {
		Method method = getDeclaredMethod(object, methodName, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + object + "]");
		}

		method.setAccessible(true);

		try {
			return method.invoke(object, parameters);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 循环向上转型,获取对象的DeclaredMethod.
	 * 
	 * 如向上转型到Object仍无法找到, 返回null.
	 */
	protected static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes) {
		Assert.notNull(object, "object不能为空");

		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredMethod(methodName, parameterTypes);
			} catch (NoSuchMethodException e) {
				// Method不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 得到所有的属性
	 * 
	 * @param entityClass
	 * @return
	 */
	public static ArrayList<Field> getAllFields(Class<?> entityClass) {
		ArrayList<Field> fs = new ArrayList<Field>();
		// 得到model所有属性
		Class<?> class1 = entityClass;
		while (!class1.getSimpleName().equals("Object")) {
			fs.addAll(Arrays.asList(class1.getDeclaredFields()));
			class1 = class1.getSuperclass();
		}
		return fs;
	}

	/**
	 * 得到对象属性的值
	 * 
	 * @param model
	 * @param prepertyName
	 * @return
	 */
	public static Object getFieldValue(Object model, String prepertyName) {
		try {
			return BeanUtils.getSimpleProperty(model, prepertyName);
			// Class<?> class1 = model.getClass();
			// Field field = class1.getDeclaredField(prepertyName);
			// field.setAccessible(true);
			// return field.get(model);
		} catch (Exception e) {
			throw ReflectUtil.convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 得到对象属性的值
	 * 
	 * @param model
	 * @param prepertyName
	 * @return
	 */
	public static void setFieldValue(Object model, String prepertyName, String value) {
		Class<?>[] plusPara = { String.class };
		Object[] transPlusPara = { value };
		try {
			model.getClass().getMethod("set" + prepertyName.substring(0, 1).toUpperCase() + prepertyName.substring(1),
					plusPara).invoke(model, transPlusPara);
		} catch (Exception e) {
			throw ReflectUtil.convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 得到对象属性的值
	 * 
	 * @param model
	 * @param prepertyName
	 * @return
	 */
	public static void setFieldCharValue(Object model, String prepertyName, Character value) {
		Class<?>[] plusPara = { Character.class };
		Object[] transPlusPara = { value };
		try {
			model.getClass().getMethod("set" + prepertyName.substring(0, 1).toUpperCase() + prepertyName.substring(1),
					plusPara).invoke(model, transPlusPara);
		} catch (Exception e) {
			throw ReflectUtil.convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 得到类属性的类型
	 * 
	 * @param class1
	 * @param propertyName
	 * @return
	 */
	public static Class<?> getFieldClass(Class<?> class1, String propertyName) {
		try {
			Field field = class1.getDeclaredField(propertyName);
			return field.getClass();
		} catch (Exception e) {
			throw ReflectUtil.convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 将反射时的checked exception转换为unchecked exception.
	 */
	public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
		if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
				|| e instanceof NoSuchMethodException) {
			return new IllegalArgumentException("Reflection Exception.", e);
		} else if (e instanceof InvocationTargetException) {
			return new RuntimeException("Reflection Exception.", ((InvocationTargetException) e).getTargetException());
		} else if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException("Unexpected Checked Exception.", e);
	}

	/**
	 * 通过反射,获得Class定义中声明的父类的泛型参数的类型. 如无法找到, 返回Object.class. eg. public UserDao extends HibernateDao<User>
	 * 
	 * @param clazz
	 *            The class to introspect
	 * @return the first generic declaration, or Object.class if cannot be determined
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射,获得定义Class时声明的父类的泛型参数的类型. 如无法找到, 返回Object.class.
	 * 
	 * 如public UserDao extends HibernateDao<User,Long>
	 * 
	 * @param clazz
	 *            clazz The class to introspect
	 * @param index
	 *            the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or Object.class if cannot be determined
	 */
	@SuppressWarnings("unchecked")
	public static Class getSuperClassGenricType(final Class clazz, final int index) {
		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[index];
	}

	/**
	 * 转换字符串到相应类型.
	 * 
	 * @param value
	 *            待转换的字符串
	 * @param toType
	 *            转换目标类型
	 */
	public static Object convertStringToObject(String value, Class<?> toType) {
		try {
			return ConvertUtils.convert(value, toType);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 得到实际的类，obj可能是hibernate的代理类，如果是代理类获取实际类型
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Class getActualClass(Object obj) {
		if (obj instanceof HibernateProxy) {
			LazyInitializer lazyInit = ((HibernateProxy) obj).getHibernateLazyInitializer();
			return lazyInit.getPersistentClass();
		}
		return obj.getClass();
	}
}
