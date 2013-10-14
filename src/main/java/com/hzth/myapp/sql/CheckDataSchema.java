package com.hzth.myapp.sql;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * 检测hbm文件和数据库是否一致
 * 
 * @author tianyl
 * 
 */
public class CheckDataSchema {

	public static void main(String[] args) {
		String baseDir = "E:/workspacedc";
		File file = new File(baseDir);
		List<File> files = new ArrayList<File>();
		for (File f : file.listFiles()) {
			if (!f.getName().startsWith("dc-")) {
				continue;
			}
			getHbmFiles(files, f);
		}
		System.out.println("共找到文件:" + files.size());
		Map<String, TableInfo> tabMap = new HashMap<String, TableInfo>();
		for (File hbmFile : files) {
			TableInfo tab = new TableInfo();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			FileInputStream fileIs = null;
			try {
				fileIs = new FileInputStream(hbmFile);
				DocumentBuilder builder = factory.newDocumentBuilder();
				builder.setEntityResolver(new EntityResolver() {
					@Override
					public InputSource resolveEntity(String arg0, String arg1) throws SAXException, IOException {
						return new InputSource(new ByteArrayInputStream("<?xml version=\"1.0\" encoding=\"utf-8\"?>".getBytes()));
					}
				});
				Document doc = builder.parse(fileIs);
				NodeList nodeList = doc.getChildNodes();
				for (int index = 0; index < nodeList.getLength(); index++) {
					Node node = nodeList.item(index);
					if (node instanceof Element) {
						NodeList nodeList2 = node.getChildNodes();
						for (int index2 = 0; index2 < nodeList2.getLength(); index2++) {
							Node node2 = nodeList2.item(index2);
							if (node2 instanceof Element) {
								String tableName = ((Element) node2).getAttribute("table");
								if (StringUtils.isBlank(tableName)) {
									System.out.println("解析table出错:" + hbmFile.getAbsolutePath());
								}
								tab.setName(tableName);
								NodeList nodeList3 = node2.getChildNodes();
								for (int index3 = 0; index3 < nodeList3.getLength(); index3++) {
									Node node3 = nodeList3.item(index3);
									if (node3 instanceof Element) {
										String tag = node3.getNodeName();
										if (tag.equals("id")) {
											ColumnInfo col = new ColumnInfo();
											col.setLength(32);
											col.setName("id");
											col.setType("char");
											tab.addColumnInfo(col);
										} else if (tag.equals("many-to-one")) {
											NodeList nodeList4 = node3.getChildNodes();
											if (nodeList4.getLength() == 0) {
												System.out.println("解析many-to-one出错，无子节点:" + hbmFile.getAbsolutePath());
											}
											for (int index4 = 0; index4 < nodeList4.getLength(); index4++) {
												Node node4 = nodeList4.item(index4);
												if (node4 instanceof Element) {
													if (node4.getNodeName().equals("column")) {
														String colName = ((Element) node4).getAttribute("name");
														if (StringUtils.isBlank(colName)) {
															System.out.println("解析many-to-one中的name出错:" + hbmFile.getAbsolutePath());
														}
														ColumnInfo col = new ColumnInfo();
														col.setLength(32);
														col.setName(colName);
														col.setType("char");
														tab.addColumnInfo(col);
													} else {
														System.out.println("解析many-to-one出错:" + hbmFile.getAbsolutePath());
													}
												}
											}
										} else if (tag.equals("property")) {
											NodeList nodeList4 = node3.getChildNodes();
											if (nodeList4.getLength() == 0) {
												System.out.println("解析property出错，无子节点:" + hbmFile.getAbsolutePath());
											}
											for (int index4 = 0; index4 < nodeList4.getLength(); index4++) {
												Node node4 = nodeList4.item(index4);
												if (node4 instanceof Element) {
													if (node4.getNodeName().equals("column")) {
														String colName = ((Element) node4).getAttribute("name");
														if (StringUtils.isBlank(colName)) {
															System.out.println("解析property中的name出错:" + hbmFile.getAbsolutePath());
														}
														ColumnInfo col = new ColumnInfo();
														String lenStr = ((Element) node4).getAttribute("length");
														if (StringUtils.isNotBlank(lenStr)) {
															col.setLength(Integer.valueOf(lenStr));
														}
														col.setName(colName);
														tab.addColumnInfo(col);
													} else {
														System.out.println("解析property出错:" + hbmFile.getAbsolutePath());
													}
												}
											}
										} else if (tag.equals("set")) {
											String setTabName = ((Element) node3).getAttribute("table");
											if (StringUtils.isNotBlank(setTabName)) {
												TableInfo setTab = new TableInfo();
												setTab.setName(setTabName);
												NodeList nodeList4 = node3.getChildNodes();
												if (nodeList4.getLength() == 0) {
													System.out.println("解析set出错，无子节点:" + hbmFile.getAbsolutePath());
												}
												boolean hasKey = false;
												boolean hasManyToMany = false;
												boolean hasOneToMany = false;
												boolean hasTab = false;
												for (int index4 = 0; index4 < nodeList4.getLength(); index4++) {
													Node node4 = nodeList4.item(index4);
													if (node4 instanceof Element) {
														if (node4.getNodeName().equals("key")) {
															hasKey = true;
															NodeList nodeList5 = node4.getChildNodes();
															String colName = ((Element) node4).getAttribute("column");
															if (StringUtils.isBlank(colName)) {
																for (int index5 = 0; index5 < nodeList5.getLength(); index5++) {
																	Node node6 = nodeList5.item(index5);
																	if (node6.getNodeName().equals("column")) {
																		if (node6 instanceof Element) {
																			colName = ((Element) node6).getAttribute("name");
																		}
																	}
																}
															}
															if (StringUtils.isNotBlank(colName)) {
																ColumnInfo col = new ColumnInfo();
																col.setLength(32);
																col.setName(colName);
																col.setType("char");
																setTab.addColumnInfo(col);
																hasTab = true;
															}
														} else if (node4.getNodeName().equals("many-to-many")) {
															hasManyToMany = true;
															NodeList nodeList5 = node4.getChildNodes();
															String colName = ((Element) node4).getAttribute("column");
															if (StringUtils.isBlank(colName)) {
																for (int index5 = 0; index5 < nodeList5.getLength(); index5++) {
																	Node node6 = nodeList5.item(index5);
																	if (node6.getNodeName().equals("column")) {
																		if (node6 instanceof Element) {
																			colName = ((Element) node6).getAttribute("name");
																		}
																	}
																}
															}
															if (StringUtils.isNotBlank(colName)) {
																ColumnInfo col = new ColumnInfo();
																col.setLength(32);
																col.setName(colName);
																col.setType("char");
																setTab.addColumnInfo(col);
																hasTab = true;
															}
														} else if (node4.getNodeName().equals("one-to-many")) {
															hasOneToMany = true;
														} else {
															System.out.println("无法解析set:" + hbmFile.getAbsolutePath());
														}
													}
												}
												if (!hasKey) {
													System.out.println("解析set出错：" + hbmFile.getAbsolutePath());
												}
												if (!hasManyToMany && !hasOneToMany) {
													System.out.println("解析set出错：" + hbmFile.getAbsolutePath());
												}
												if (hasTab && !hasOneToMany) {
													if (setTab.getColumnInfoMap().entrySet().isEmpty()) {
														System.out.println("解析set出错，没找到列信息：" + hbmFile.getAbsolutePath());
													}
													for (String colKey : setTab.getColumnInfoMap().keySet()) {
														if (StringUtils.isBlank(colKey)) {
															System.out.println("解析set出错，出现空列：" + hbmFile.getAbsolutePath());
														}
													}
													if (tabMap.containsKey(setTab.getName())) {
														boolean same = true;
														for (String key2 : setTab.getColumnInfoMap().keySet()) {
															if (!tabMap.get(setTabName).getColumnInfoMap().keySet().contains(key2)) {
																same = false;
															}
														}
														if (!same) {
															System.out.println("set解析出现错误，table重名:" + ":" + setTab.getName() + ":" + hbmFile.getAbsolutePath());
														}
													}
													tabMap.put(setTabName, setTab);
												}
											}
										} else if (tag.equals("one-to-one")) {

										} else {
											System.out.println(hbmFile.getAbsolutePath() + " 不支持:" + tag);
										}
									}
								}
							}
						}
					}
				}
				// break;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fileIs != null) {
					try {
						fileIs.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			if (tabMap.containsKey(tab.getName())) {
				System.out.println("解析出现错误，table重名");
			}
			tabMap.put(tab.getName(), tab);
		}

		// for (String tab : tabMap.keySet()) {
		// TableInfo table = tabMap.get(tab);
		// if (table.getName().equals("qn_option")) {
		// for (String col : table.getColumnInfoMap().keySet()) {
		// System.out.println(table.getColumnInfoMap().get(col).getName());
		// }
		// }
		// }
		Connection conn = null;
		try {
			conn = SqlHelper.getSqlServerConnection("192.168.1.122", "aaa", "sa", "hzth-801");
			Map<String, TableInfo> tabMap2 = CreateDBSchema.getTableInfo(conn);
			showTableDiff(tabMap, tabMap2);
			System.out.println("----------------------");
			showTableDiff(tabMap2, tabMap);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlHelper.close(conn);
		}
	}

	private static void showTableDiff(Map<String, TableInfo> tabMap, Map<String, TableInfo> tabMap2) {
		for (String key : tabMap.keySet()) {
			TableInfo tableInfo = tabMap.get(key);
			if (!tabMap2.containsKey(tableInfo.getName())) {
				System.out.println("不存在表：" + tableInfo.getName());
				continue;
			}
			TableInfo tableInfo2 = tabMap2.get(key);
			for (String colkey : tableInfo.getColumnInfoMap().keySet()) {
				if (!tableInfo2.getColumnInfoMap().containsKey(colkey)) {
					System.out.println("表" + tableInfo.getName() + "不存在列：" + colkey);
					continue;
				}
				// ColumnInfo info = tableInfo.getColumnInfoMap().get(colkey);
				// ColumnInfo info2 = tableInfo2.getColumnInfoMap().get(colkey);
				// if (info.getLength() != null && info2.getLength() != null) {
				// if (info.getLength() - info2.getLength() != 0) {
				// System.out.println("表" + tableInfo.getName() + ",列：" + colkey + "长度不同");
				// continue;
				// }
				// }
			}
		}
	}

	private static void getHbmFiles(List<File> files, File file) {
		for (File f : file.listFiles()) {
			if (f.getAbsolutePath().indexOf("webapp\\WEB-INF") != -1) {
				continue;
			}
			if (f.isDirectory()) {
				getHbmFiles(files, f);
			} else {
				if (f.getName().endsWith(".hbm.xml")) {
					files.add(f);
				}
			}
		}
	}
}
