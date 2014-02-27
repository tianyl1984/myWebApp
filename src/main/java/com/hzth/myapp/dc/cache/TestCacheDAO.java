package com.hzth.myapp.dc.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

@Repository("ex_TestCacheDAO")
public class TestCacheDAO {

	// @CacheInPersistence(ignoreExpiredKeyCommaSplit = true)
	// @CacheInPersistence
	public Attachment getAttachment(String id) {
		System.out.println("getAttachment:" + id);
		Attachment att = new Attachment();
		att.setId(id);
		return att;
	}

	// @CacheInPersistence(expiredKeyPosition = 1)
	public Attachment getAttachment1(String id, String id2) {
		System.out.println("getAttachment1:" + id + "," + id2);
		Attachment att = new Attachment();
		att.setId(id);
		return att;
	}

	// @CacheInPersistence(expiredKeyPosition = 1, expiredKeyExecutor = TestExecutor.class)
	public Attachment getAttachment2(String id, String id2) {
		System.out.println("getAttachment2:" + id + "," + id2);
		Attachment att = new Attachment();
		att.setId(id);
		return att;
	}

	// @CacheInPersistence
	public Attachment getAttachment3(String id, int i, boolean b, char c, byte bt, short s, long l, float f, double d) {
		System.out.println("getAttachment3:" + id + ",");
		Attachment att = new Attachment();
		att.setId(id);
		return att;
	}

	// @CacheInPersistence
	public Attachment getAttachment4(String id, List<String> list, Set<String> set, Map<String, String> map) {
		System.out.println("getAttachment4:" + id + ",");
		Attachment att = new Attachment();
		att.setId(id);
		return att;
	}

	// @CacheInPersistence
	public String getString(String id) {
		System.out.println("getString");
		return id;
	}

	// Integer Boolean Character Byte Short Long Float Double

	@CacheInPersistence
	public Integer getInteger(String id) {
		System.out.println("getInteger");
		return 123;
	}

	@CacheInPersistence
	public int getInt(String id) {
		System.out.println("getInt");
		return 123456;
	}

	@CacheInPersistence
	public long getLong(String id) {
		System.out.println("getLong");
		return 123L;
	}

	@CacheInPersistence
	public boolean getBoolean(String id) {
		System.out.println("getBoolean");
		return true;
	}

	@CacheInPersistence
	public char getChar(String id) {
		System.out.println("getChar");
		return '中';
	}

	@CacheInPersistence
	public byte getByte(String id) {
		System.out.println("getByte");
		return 1;
	}

	@CacheInPersistence
	public short getShort(String id) {
		System.out.println("getShort");
		return 1;
	}

	@CacheInPersistence
	public float getFloat(String id) {
		System.out.println("getFloat");
		return 1.2f;
	}

	@CacheInPersistence
	public double getDouble(String id) {
		System.out.println("getDouble");
		return 1.2;
	}

	@CacheInPersistence
	public List<String> getListStr(String id) {
		System.out.println("getListStr");
		List<String> result = new ArrayList<String>();
		result.add("12");
		result.add("23");
		result.add("34");
		result.add("45asd");
		return result;
	}

	@CacheInPersistence
	public List<Integer> getListInteger(String id) {
		System.out.println("getListInteger");
		List<Integer> result = new ArrayList<Integer>();
		result.add(12);
		result.add(212);
		result.add(132);
		result.add(142);
		result.add(152);
		result.add(162);
		result.add(172);
		return result;
	}

	@CacheInPersistence
	public Map<String, Attachment> getAttachmentMap(String id) {
		System.out.println("getAttachmentMap");
		Map<String, Attachment> map = new HashMap<String, Attachment>();
		for (Attachment att : getAttachmentList(id, "")) {
			map.put(att.getId(), att);
		}
		return map;
	}

	@CacheInPersistence
	public List<Attachment> getAttachmentList(String id, String name) {
		System.out.println("getAttachmentList");
		Attachment att = new Attachment();
		att.setId("123456789");
		Attachment att2 = new Attachment();
		att2.setId("abcdefgh");
		List<Attachment> result = new ArrayList<Attachment>();
		result.add(att);
		result.add(att2);
		return result;
	}
}
