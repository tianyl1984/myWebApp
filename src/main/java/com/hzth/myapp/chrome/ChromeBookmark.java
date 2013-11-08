package com.hzth.myapp.chrome;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ChromeBookmark {

	public static void main(String[] args) throws Exception {
		String filePath = "C:/Users/tianyl/AppData/Local/Google/Chrome/User Data/Default/Bookmarks";
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath))));
		String temp = null;
		while ((temp = br.readLine()) != null) {
			sb.append(temp);
		}
		br.close();
		JSONObject json = JSONObject.fromObject(sb.toString());
		JSONArray roots = json.getJSONObject("roots").getJSONObject("bookmark_bar").getJSONArray("children");
		int count = 0;
		for (int i = 0; i < roots.size(); i++) {
			JSONObject obj = (JSONObject) roots.get(i);
			String name = obj.get("name").toString();
			if (name.equals("收藏")) {
				JSONArray tagets = obj.getJSONArray("children");
				for (int j = 0; j < tagets.size(); j++) {
					JSONObject obj2 = (JSONObject) tagets.get(j);
					if (obj2.getString("type").equals("url")) {
						System.out.println(obj2.getString("name") + "   " + obj2.getString("url"));
						count++;
					}
				}
			}
		}
		System.out.println(count);
	}
}
