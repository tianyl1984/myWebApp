package com.hzth.myapp.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.lang.StringUtils;

import com.hzth.myapp.user.model.Teacher;
import com.hzth.myapp.user.model.User;

public class JsonUtil {

	/**
	 * 把json串转成对象
	 * 
	 * @param jsonString
	 *            json字符串
	 * @param pojoClass
	 *            要转成的class，必须是public的
	 * @param map
	 * @return
	 */
	public static <T> T json2JavaPojo(String jsonString, Class<T> pojoClass, Map<String, Class<? extends Object>> map) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		@SuppressWarnings("unchecked")
		T pojo = (T) JSONObject.toBean(jsonObject, pojoClass, map);
		return pojo;
	}

	/**
	 * 把json串转成Map
	 * 
	 * @param jsonString
	 * @return key和value对应json中的key和value
	 */
	public static Map<String, String> json2Map(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		@SuppressWarnings("unchecked")
		Iterator<String> keyIter = jsonObject.keys();
		Map<String, String> valueMap = new HashMap<String, String>();

		while (keyIter.hasNext()) {
			String key = (String) keyIter.next();
			String value = jsonObject.get(key).toString();
			valueMap.put(key, value);
		}

		return valueMap;
	}

	/**
	 * 把json串转成字符串数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static String[] json2StringArray(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		String[] stringArray = new String[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			stringArray[i] = jsonArray.getString(i);
		}
		return stringArray;
	}

	/**
	 * 把json串转成长整型数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Long[] json2LongArray(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Long[] longArray = new Long[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			longArray[i] = jsonArray.getLong(i);
		}
		return longArray;
	}

	/**
	 * 把json串转成整型数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Integer[] json2IntegerArray(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Integer[] integerArray = new Integer[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			integerArray[i] = jsonArray.getInt(i);
		}
		return integerArray;
	}

	/**
	 * 把json串转成日期数组
	 * 
	 * @param jsonString
	 * @param dataFormat
	 * @return
	 */
	public static Date[] json2DateArray(String jsonString, String dataFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dataFormat);
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Date[] dateArray = new Date[jsonArray.size()];
		String dateString;
		try {
			for (int i = 0; i < jsonArray.size(); i++) {
				dateString = jsonArray.getString(i);
				Date date = sdf.parse(dateString);
				dateArray[i] = date;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateArray;
	}

	/**
	 * 把json串转成Double数组
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Double[] json2DoubleArra(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Double[] doubleArray = new Double[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			doubleArray[i] = jsonArray.getDouble(i);
		}
		return doubleArray;
	}

	/**
	 * 把json串转成对象集合
	 * 
	 * @param jsonString
	 * @param pojoClass
	 * @return
	 */
	public static <T> List<T> json2List(String jsonString, Class<T> pojoClass) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONObject jsonObject;
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < jsonArray.size(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			@SuppressWarnings("unchecked")
			T pojoValue = (T) JSONObject.toBean(jsonObject, pojoClass);
			list.add(pojoValue);
		}
		return list;
	}

	/**
	 * 把java对象转换成json字符串
	 * 
	 * @param object
	 * @return
	 */
	public static String javaPojo2Json(Object object) {
		JSONObject json;
		json = JSONObject.fromObject(object);
		return json.toString();
	}

	/**
	 * 把java对象集合转换成json字符串
	 * 
	 * @param list
	 * @return
	 */
	public static String getJsonString4JavaCollections(List<?> list) {
		JSONArray jsonArray = JSONArray.fromObject(list);
		return jsonArray.toString();
	}

	/**
	 * 将Map转换成json字符串
	 * 
	 * @param map
	 * @return
	 */
	public static String map2Json(Map<?, ?> map) {
		JSONObject json = JSONObject.fromObject(map);
		return json.toString();
	}

	/**
	 * json串转成xml
	 * 
	 * @param jsonString
	 * @return
	 */
	public static String json2Xml(String jsonString, String rootName) {
		JSONObject json = JSONObject.fromObject(jsonString);
		XMLSerializer xmlSerializer = new XMLSerializer();
		xmlSerializer.setRootName(rootName);
		return xmlSerializer.write(json);
	}

	public static String javaPojo2Json(final Object object, final List<String> includeProperties) {
		JSONObject json = new JSONObject();
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] fields = object.getClass().getDeclaredFields();
		for (Field f : fields) {
			try {
				String key = f.getName();
				f.setAccessible(true);
				if (Modifier.isFinal(f.getModifiers()) || Modifier.isStatic(f.getModifiers())) {
					map.put(key, f.get(object));
					continue;
				}
				if (isBaseType(f.getType()) || f.getType() == String.class) {
					map.put(key, invokeGetterMethod(object, key));
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		json = JSONObject.fromObject(map);
		for (String prop : includeProperties) {
			fillValue(json, prop, object);
		}
		return json.toString();
	}

	public static String collection2Json(Collection<? extends Object> collections) {
		return collection2Json(collections, null);
	}

	public static String collection2Json(Collection<? extends Object> collections, List<String> includeProperties) {
		if (includeProperties == null) {
			includeProperties = new ArrayList<String>();
		}
		JSONArray result = new JSONArray();
		if (collections.size() == 0) {
			return result.toString();
		} else {
			Object obj = collections.iterator().next();
			if (obj instanceof Map) {// Map
				return JSONArray.fromObject(collections).toString();
			}
		}
		for (Object object : collections) {
			if (object == null) {
				continue;
			}
			JSONObject json = new JSONObject();
			Map<String, Object> map = new HashMap<String, Object>();
			@SuppressWarnings("rawtypes")
			Class clazz = ReflectUtil.getActualClass(object);
			Field[] fields = clazz.getDeclaredFields();
			for (Field f : fields) {
				try {
					String key = f.getName();
					f.setAccessible(true);
					if (Modifier.isFinal(f.getModifiers()) || Modifier.isStatic(f.getModifiers())) {
						map.put(key, f.get(object));
						continue;
					}
					if (isBaseType(f.getType()) || f.getType() == String.class) {
						map.put(key, invokeGetterMethod(object, key));
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			json = JSONObject.fromObject(map);
			for (String prop : includeProperties) {
				fillValue(json, prop, object);
			}
			result.add(json);
		}
		return result.toString();
	}

	private static Object invokeGetterMethod(Object target, String propertyName) {
		String getterMethodName = "get" + StringUtils.capitalize(propertyName);
		Method m = getGetterMethod(getterMethodName, target);
		if (m == null) {
			m = getGetterMethod("is" + StringUtils.capitalize(propertyName), target);
		}
		try {
			return m.invoke(target);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Method getGetterMethod(String getterMethodName, Object target) {
		Method[] methods = target.getClass().getDeclaredMethods();
		for (Method m : methods) {
			if (m.getParameterTypes() == null || m.getParameterTypes().length == 0) {
				if (m.getName().endsWith(getterMethodName)) {
					return m;
				}
			}
		}
		return null;
	}

	private static void fillValue(JSONObject json, String prop, Object object) {
		if (object == null) {
			return;
		}
		if (prop.contains(".")) {
			String key = prop.substring(0, prop.indexOf("."));
			if (!json.containsKey(key)) {
				JSONObject jsonObj = new JSONObject();
				Object value = invokeGetterMethod(object, key);
				fillValue(jsonObj, prop.replaceFirst(key + ".", ""), value);
				json.accumulate(key, jsonObj);
			} else {
				JSONObject jsonObj = json.getJSONObject(key);
				Object value = invokeGetterMethod(object, key);
				fillValue(jsonObj, prop.replaceFirst(key + ".", ""), value);
				json.remove(key);
				json.accumulate(key, jsonObj);
			}
		} else {
			Object value = invokeGetterMethod(object, prop);
			json.accumulate(prop, value);
		}
	}

	// private static List<String> getCollectionProperties(Object object) {
	// List<String> result = new ArrayList<String>();
	// Field[] fields = object.getClass().getDeclaredFields();
	// for (Field f : fields) {
	// // System.out.println("GenericType:" + f.getGenericType());
	// // System.out.println("Type:" + f.getType());
	// // System.out.println("DeclaringClass:" + f.getDeclaringClass());
	// // System.out.println(f.getType().isArray());
	// // System.out.println(f.getType() + ":" + f.getType().isMemberClass());
	// // System.out.println(f.getType() + ":" + f.getType().getSuperclass());
	// // System.out.println(f.getType() + ":" + f.getType().isInstance(new
	// ArrayList()));
	// // System.out.println(f.getType() + ":" +
	// f.getType().isAssignableFrom(List.class));
	// // System.out.println(f.getType() + ":" +
	// f.getType().getInterfaces().length);
	// // if (f.getType().getInterfaces().length > 0) {
	// // String temp = "";
	// // for (Class c : f.getType().getInterfaces()) {
	// // temp += c.getName() + ":";
	// // }
	// // System.out.println(temp);
	// // }
	// System.out.println(f.getType() + ":" + f.getType());
	//
	// }
	// return result;
	// }

	private static boolean isBaseType(Class<?> type) {
		if (type.isPrimitive()) {// int char 类型
			return true;
		}
		if (type == Integer.class || type == Boolean.class || type == Character.class || type == Byte.class || type == Short.class || type == Long.class || type == Float.class || type == Double.class) {
			return true;
		}
		return false;
	}

	public static String javaPojo2Json2(final Object object, final List<String> includeProperties) {
		JSONObject json = new JSONObject();
		JsonConfig jsonConfig = new JsonConfig();
		String[] excludes = getExcludes(object, includeProperties);
		jsonConfig.setExcludes(excludes);
		json = JSONObject.fromObject(object, jsonConfig);
		return json.toString();
	}

	private static String[] getExcludes(Object object, List<String> includeProperties) {
		List<String> excludes = new ArrayList<String>();
		Field[] fields = object.getClass().getDeclaredFields();
		for (Field f : fields) {
			String key = f.getName();
			if (Modifier.isFinal(f.getModifiers()) || Modifier.isStatic(f.getModifiers())) {
				continue;
			}
			if (isBaseType(f.getType()) || f.getType() == String.class) {
				continue;
			}
			boolean flag = true;
			for (String prop : includeProperties) {
				if (prop.startsWith(key + ".")) {
					flag = false;
				}
			}
			if (flag) {
				excludes.add(key);
			}
		}
		return null;
	}

	public static void main(String[] args) {
		User user = new User("id123", "张三", "zhangsan@126.com");
		user.setTeacher(Teacher.getATeacher());
		user.setParent(new User("asdf", "parent", "afsdfdsfa"));
		user.addUser(new User("dsf", "saf", "safdsad"));
		// long t1 = System.currentTimeMillis();
		// for (int i = 0; i < 10000; i++) {
		// javaPojo2Json(user, null);
		// }
		// System.out.println(System.currentTimeMillis() - t1);
		List<String> strs = new ArrayList<String>();
		strs.add("teacher.name");
		// strs.add("teacher.id");
		strs.add("parent.name");
		strs.add("parent.teacher.name");

		User user2 = new User("id123", "张三2", "zhangsan@126.com");
		User user3 = new User("id123", "张三3", "zhangsan@126.com");
		User user4 = new User("id123", "张三4", "zhangsan@126.com");
		List<User> users = new ArrayList<User>();
		users.add(user);
		users.add(user2);
		users.add(user3);
		users.add(user4);
		System.out.println(collection2Json(users, strs));
		// System.out.println(javaPojo2Json(user, strs));
		// List<Teacher> tt = new ArrayList<Teacher>();
		// tt.add(new Teacher("afsd", "asfd"));
		// tt.add(new Teacher("afsdasdf", "asfdafsd"));
		// User user2 = new User("id123123", "张三123", "zhangsan@126.com");
		// List<User> users = new ArrayList<User>();
		// users.add(user);
		// users.add(user2);
		// System.out.println(javaPojo2Json(users));
		// System.out.println(getJsonString4JavaCollections(users));
		// String jsonString = getJsonString4JavaCollections(users);
		// List<User> users2 = json2List(jsonString, User.class);
		// Map<String, Class<? extends Object>> map = new HashMap<String,
		// Class<? extends Object>>();
		// map.put("students", Student.class);
		// map.put("stus", Student.class);
		// String jsonString = javaPojo2Json(user);
		// List<User> users2 = new ArrayList<User>();
		// users2.add(json2JavaPojo(jsonString, User.class, map));
		// for (User u : users2) {
		// System.out.println(u);
		// if (u.getTeacher() != null) {
		// System.out.println("teacher:" + u.getTeacher());
		// if (u.getTeacher().getStudents() != null) {
		// for (Student s : u.getTeacher().getStudents()) {
		// System.out.println("student:" + s);
		// if (s.getAddress() != null) {
		// System.out.println("address:" + s.getAddress());
		// }
		// }
		// }
		// }
		// }
	}

}
