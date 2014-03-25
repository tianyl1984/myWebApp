package com.hzth.myapp.svn;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.hzth.myapp.encode.Base64Util;
import com.hzth.myapp.web.NetUtil;

public class SvnDemo2 {

	private static final String baseUrl = "https://192.168.1.8/svn/dc-v3/java/";
	private static final String name = "tianyale";
	private static final String password = "tianyale";
	private static String authorization = "";

	public static void main(String[] args) throws Exception {
		findFile("poi-3.2-FINAL.jar");
	}

	private static void findFile(String fileName) throws Exception {
		String str = name + ":" + password;
		authorization = "Basic " + Base64Util.encryptBASE64(str.getBytes());
		authorization = "Basic dGlhbnlhbGU6dGlhbnlhbGU=";
		recursionFile(baseUrl, fileName);
	}

	private static void recursionFile(String url, String fileName) throws Exception {
		// System.out.println(url);
		SvnStruct svnStruct = parseSvnStruct(url);
		for (SvnStructItem item : svnStruct.getItems()) {
			if (item.getName().equals(fileName)) {
				System.out.println(item.getUrl());
			}
			String name = item.getName();
			if (name.equals("tags") || name.equals("branches")) {
				continue;
			}
			if (!name.startsWith("dc-")) {
				if (!name.equals("trunk") && !name.equals("webapp") && !name.equals("WEB-INF") && !name.equals("tld") && !name.equals("lib")) {
					continue;
				}
			}
			if (!item.isFile()) {
				recursionFile(item.getUrl(), fileName);
			}
		}
	}

	private static SvnStruct parseSvnStruct(String url) throws Exception {
		String resultStr = NetUtil.getHttpsResponse(url, authorization);
		SvnStruct result = new SvnStruct();
		Document doc = Jsoup.parse(resultStr);
		Element index = doc.getElementsByTag("index").first();
		result.setRev(index.attr("rev"));
		result.setPath(index.attr("path"));
		result.setBase(index.attr("base"));
		result.setUrl(url);
		for (Element ele : index.children()) {
			if (StringUtils.isBlank(ele.nodeName())) {
				continue;
			}
			if (ele.nodeName().equals("updir")) {
				continue;
			}
			result.addItem(ele.nodeName().equals("file"), ele.attr("name"), ele.attr("href"));
		}
		return result;
	}
}

class SvnStruct {
	private String rev;
	private String path;
	private String base;
	private String url;
	private List<SvnStructItem> items = new ArrayList<>();

	public SvnStruct() {

	}

	public SvnStruct(String rev, String path, String base, String url) {
		super();
		this.rev = rev;
		this.path = path;
		this.base = base;
		this.url = url;
	}

	public String getRev() {
		return rev;
	}

	public void setRev(String rev) {
		this.rev = rev;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public void addItem(boolean isFile, String name, String href) {
		SvnStructItem item = new SvnStructItem(isFile, name, href);
		item.setSvnStruct(this);
		this.items.add(item);
	}

	public List<SvnStructItem> getItems() {
		return items;
	}

	public void setItems(List<SvnStructItem> items) {
		this.items = items;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}

class SvnStructItem {
	private boolean isFile;
	private String name;
	private String href;
	private SvnStruct svnStruct;

	public SvnStructItem(boolean isFile, String name, String href) {
		super();
		this.isFile = isFile;
		this.name = name;
		this.href = href;
	}

	public boolean isFile() {
		return isFile;
	}

	public void setFile(boolean isFile) {
		this.isFile = isFile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public SvnStruct getSvnStruct() {
		return svnStruct;
	}

	public void setSvnStruct(SvnStruct svnStruct) {
		this.svnStruct = svnStruct;
	}

	public String getUrl() {
		return svnStruct.getUrl() + "/" + href;
	}
}