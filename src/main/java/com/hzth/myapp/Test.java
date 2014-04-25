package com.hzth.myapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.hzth.myapp.core.util.StringUtil;
import com.hzth.myapp.core.util.UUID;

public class Test {

	private static String options = "";
	private static String width = "";
	private static String scale = "";
	private static String constr = "";
	private static String callback = "";
	static {
		StringBuffer optionsBuffer = new StringBuffer();
		optionsBuffer.append("{");
		optionsBuffer.append("	title:{");
		optionsBuffer.append("		text:'统计图'");
		optionsBuffer.append("	},");
		optionsBuffer.append("	xAxis: {");
		optionsBuffer.append("		categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']");
		optionsBuffer.append("	},");
		optionsBuffer.append("	series: [{");
		optionsBuffer.append("		data: [29.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]");
		optionsBuffer.append("	}]");
		optionsBuffer.append("};");
		options = optionsBuffer.toString();
		width = "width";
		scale = "1";
		constr = "Chart";
		StringBuffer callbackBuffer = new StringBuffer();
		callbackBuffer.append("function(chart) {");
		callbackBuffer.append("	chart.renderer.arc(200, 150, 100, 50, -Math.PI, 0).attr({");
		callbackBuffer.append("		fill : '#FCFFC5',");
		callbackBuffer.append("		stroke : 'black',");
		callbackBuffer.append("		'stroke-width' : 1");
		callbackBuffer.append("	}).add();");
		callbackBuffer.append("}");
		callback = callbackBuffer.toString();
	}

	public static void main(String[] args) throws Exception {
		// m1();
		// m2();
		// m3();
		// m4();
		// System.out.println("aaa.a".contains("."));
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("e:\\a.txt"))));
		List<String> list = new ArrayList<>();
		String temp = null;
		while ((temp = br.readLine()) != null) {
			if (StringUtils.isNotBlank(temp)) {
				for (String num : temp.split(",")) {
					if (!list.contains(num.trim())) {
						list.add(num.trim());
					}
				}
			}
		}
		Collections.sort(list);
		for (String str : list) {
			System.out.println(str);
		}
	}

	private static void m4() throws Exception {
		List<String> command = new ArrayList<String>();
		command.add("D:\\phantomjs-1.9.7\\phantomjs.exe");
		command.add("C:\\Users\\tianyl\\Desktop\\Highcharts-3.0.10\\exporting-server\\java\\highcharts-export\\highcharts-export-convert\\src\\main\\resources\\phantomjs\\highcharts-convert.js");
		// command.add("D:\\phantomjs-1.9.7\\examples\\arguments.js");
		command.add("-infile");
		command.add("E:\\测试文件\\chart.js");
		command.add("-outfile");
		command.add("E:/测试文件/" + UUID.getUUID() + ".png");
		command.add("-constr");
		command.add(constr);
		command.add("-width");
		command.add(width);
		command.add("-scale");
		command.add(scale);

		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command(command);
		final Process process = processBuilder.start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
					String temp = null;
					while ((temp = br.readLine()) != null) {
						System.out.println(temp);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
					String temp = null;
					while ((temp = br.readLine()) != null) {
						System.out.println(temp);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		process.waitFor();// 等待子进程的结束，子进程就是系统调用文件转换这个新进程
	}

	private static void m3() throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		// map.put("outfile", "E:/测试文件/" + UUID.getUUID() + ".pdf");
		map.put("infile", options);
		map.put("constr", constr);
		// map.put("callback", callback);
		// map.put("globalOptions", "");
		// map.put("dataoptions", "");
		// map.put("customcode", "");
		map.put("width", width);
		map.put("scale", scale);
		// map.put("type", "pdf");

		String params = JSONObject.fromObject(map).toString();

		URLConnection connection = new URL("http://127.0.0.1:7777").openConnection();
		connection.setDoOutput(true);

		OutputStream out = connection.getOutputStream();
		out.write(params.getBytes("utf-8"));
		out.close();
		InputStream in = connection.getInputStream();
		String response = IOUtils.toString(in, "utf-8");
		in.close();
		System.out.println(response);
	}

	private static void m2() {
		String str = "<value><base64>iVBORw0KGgoAAAANSUhEUgAAAGsAAAATCAMAAACgEAOSAAAAAXNSR0IArs4c6QAAAAlwSFlzAAAOxAAADsQBlSsOGwAAAH5QTFRFAAAAAAA6AABmADqQAGZmAGa2OgAAOgBmOjqQOma2OpDbZgAAZgBmZjqQZmYAZma2ZpDbZrb/kDoAkDo6kDpmkJDbkLb/kNv/tmYAtmY6tmZmtpCQtrZmtrb/tv//25A625CQ29v/2////7Zm/7aQ/9uQ/9vb//+2///b////v7LgbwAAAUtJREFUSMflVG1TwyAMjpt1jqqz86WdL62WIc3//4MGKBDopne76bmTD72GhDzPkwQAf2/Bv8PqiuOhNGLEagAEdjDvE7csaE/weDirGZPEwqaK/8M6yTWsKdL6wRjm57nPqNCmZFiywi6mULeGTHQCw9pyEqguhGNusfRVS5+HHbI5Fo6Bbn30XIpeMYNkJSJFaAiMmI4MxZlVjExyrBVXMgvA+NRyrHddxnNqcWPS+Rp2woZnujb1BOstiYkVpfLyfhGp61Bsef6qywrV0mINd3WgHHS5HqZYGfJw3yI7lDhj882hzrSemIFLu6knY5rNhimqeuRNX+6Zw+Qc9diIGXWhLgGqyZWwMw+F9KNNBsxemDXvZRx7wvKWtP0OPmmT+359df0w1XKw5edwD5aTqi75vTvUCvdr95JHfKK+q+HPvId/4J0/baxPLhgsIM3wvU8AAAAASUVORK5CYII=</base64></value>";
		str = "<struct><member><name>name</name><value><string>040314_0600_1.png</string></value></member><member>";
		// System.out.println(str.replaceFirst(".*<base64>", ""));
		String result = str.replaceFirst(".*<name>name</name><value><string>", "").replaceFirst("</string>.*", "");
		System.out.println(result);
	}

	private static void m1() throws Exception {
		String url = "http://www.wandoujia.com/qr?c=aaaaaaa";
		url += StringUtil.getRandomStr(10000);
		// url = "http://127.0.0.1:8082/dc-framework/dm/demo!testBlog.action";
		HttpURLConnection conn = (HttpURLConnection) (new URL(url).openConnection());
		conn.setDoInput(true);
		conn.setDoOutput(true);
		// POST必须大写
		conn.setRequestMethod("POST");
		conn.setUseCaches(false);
		// 仅对当前请求自动重定向
		conn.setInstanceFollowRedirects(true);
		// header 设置编码
		conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
		conn.setRequestProperty("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		conn.setRequestProperty("UserAgent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:28.0) Gecko/20100101 Firefox/28.0");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		// 连接
		conn.connect();
		OutputStream outs = conn.getOutputStream();
		String aaa = "c=aaaaaaa";
		// outs.write(aaa.getBytes());
		// outs.flush();
		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			// printHeader(conn);
			// throw new IOException("response code:" + conn.getResponseCode());
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String result = "";
		String temp = null;
		while ((temp = reader.readLine()) != null) {
			result += temp + "\n";
		}
		reader.close();
		conn.disconnect();
		System.out.println(result);
		System.out.println("OK");
	}
}
