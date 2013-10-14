package com.hzth.myapp.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class HttpClientTest {

	public static void main(String[] args) {
		m1();
	}

	private static void m1() {
		try {
			// 初始化HttpClient对象
			HttpClient httpClient = new DefaultHttpClient();
			// 创建post连接
			HttpPost post = new HttpPost("http://192.168.1.122:9000/myWebApp/appServlet");
			// 构造提交的数据
			List<NameValuePair> requestData = new ArrayList<NameValuePair>();
			requestData.add(new BasicNameValuePair("userName", "哈哈哈"));
			requestData.add(new BasicNameValuePair("email", "sdfjkljsdlk"));
			// 对发送的数据编码
			//			HttpEntity<String> httpEntity = new UrlEncodedFormEntity(requestData, HTTP.UTF_8);
			//			post.setHeader("Content-Type", "text/html");
			//			post.setEntity(httpEntity);
			//			// 发送请求， 读取返回数据
			//			HttpResponse response = httpClient.execute(post);
			//			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
			//				String responseMsg = EntityUtils.toString(response.getEntity());
			//				System.out.println(responseMsg);
			//			} else {
			//				System.out.println(response.getStatusLine().getStatusCode());
			//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
