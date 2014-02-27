package com.hzth.myapp.core.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Properties;

/**
 * 属性文件工具类
 * 
 */
public class PropertyUtil {

	/**
	 * 得到属性文件的值
	 * 
	 * @param fileName
	 *            文件名称，不需要"/"
	 * @param name
	 *            属性文件key
	 * @return
	 */
	public static String getProperty(String fileName, String name) {
		ClassLoader cl = PropertyUtil.class.getClassLoader();
		InputStream is = null;
		String str = null;
		try {
			is = new FileInputStream(URLDecoder.decode(cl.getResource("/" + fileName).getFile(), "utf-8"));
			if (is != null) {
				Properties props = new Properties();
				try {
					props.load(is);
				} catch (IOException e) {
					props = null;
				}
				if (props != null) {
					str = props.getProperty(name);
					if (str != null) {
						str = str.trim();
					}
				}
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return str;
	}

	/**
	 * 保存属性文件的值
	 */
	public static void storeProperty(String fileName, String key, String value) {
		ClassLoader cl = PropertyUtil.class.getClassLoader();
		String common = "gen date:" + DateUtil.getCurrentDate();
		InputStream is = null;
		OutputStream out = null;
		try {
			is = new FileInputStream(cl.getResource("/" + fileName).getFile());
			Properties props = new Properties();
			props.load(is);
			out = new FileOutputStream(cl.getResource("/" + fileName).getFile());
			if (out != null) {
				props.setProperty(key, value);
				props.store(out, common);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private Properties propertie;
	private FileInputStream inputFile;
	private FileOutputStream outputFile;

	/**
	 * 初始化ProperUtil类
	 */
	public PropertyUtil() {
		propertie = new Properties();
	}

	/**
	 * 初始化ProperUtil类
	 * 
	 * @param filePath
	 *            要读取的配置文件的路径+名称
	 */
	public PropertyUtil(String filePath) {
		propertie = new Properties();
		try {
			inputFile = new FileInputStream(URLDecoder.decode(filePath, "utf-8"));
			propertie.load(inputFile);
			inputFile.close();
		} catch (FileNotFoundException ex) {
			System.out.println("读取属性文件--->失败！- 原因：文件路径错误或者文件不存在");
			ex.printStackTrace();
		} catch (IOException ex) {
			System.out.println("装载文件--->失败!");
			ex.printStackTrace();
		}
	}// end ReadConfigInfo()

	/**
	 * 重载函数，得到key的值
	 * 
	 * @param key
	 *            取得其值的键
	 * @return key的值
	 */
	public String getValue(String key) {
		if (propertie.containsKey(key)) {
			String value = propertie.getProperty(key);// 得到某一属性的值
			return value;
		} else
			return "";
	}// end getValue()

	/**
	 * 重载函数，得到key的值
	 * 
	 * @param fileName
	 *            properties文件的路径+文件名
	 * @param key
	 *            取得其值的键
	 * @return key的值
	 */
	public String getValue(String fileName, String key) {
		try {
			String value = "";
			inputFile = new FileInputStream(URLDecoder.decode(fileName, "utf-8"));
			propertie.load(inputFile);

			if (propertie.containsKey(key)) {
				value = propertie.getProperty(key);
				return value;
			} else
				return value;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		} finally {
			try {
				inputFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}// end getValue()

	/**
	 * 清除properties文件中所有的key和其值
	 */
	public void clear() {
		propertie.clear();
	}// end clear();

	/**
	 * 改变或添加一个key的值，当key存在于properties文件中时该key的值被value所代替， 当key不存在时，该key的值是value
	 * 
	 * @param key
	 *            要存入的键
	 * @param value
	 *            要存入的值
	 */
	public void setValue(String key, String value) {
		propertie.setProperty(key, value);
	}// end setValue()

	/**
	 * 将更改后的文件数据存入指定的文件中，该文件可以事先不存在。
	 * 
	 * @param fileName
	 *            文件路径+文件名称
	 * @param description
	 *            对该文件的描述
	 */
	public void saveFile(String fileName, String description) {
		try {
			outputFile = new FileOutputStream(URLDecoder.decode(fileName, "utf-8"));
			propertie.store(outputFile, description);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				outputFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}// end saveFile()

	public Properties getPropertie() {
		return this.propertie;
	}

}
