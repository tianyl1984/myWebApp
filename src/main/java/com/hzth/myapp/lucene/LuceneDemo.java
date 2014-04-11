package com.hzth.myapp.lucene;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

import com.hzth.myapp.core.util.UUID;

public class LuceneDemo {

	private static final File luceneDir = new File("E:\\测试文件\\lucene");

	public static void main(String[] args) throws Exception {

		// index();
		// search();
		// delete();
		// update();
		// search2();
		search3();
	}

	private static void search3() throws Exception {
		File luceneTrueDir = new File("E:/lucene/aaa/bbb");
		DirectoryReader reader = DirectoryReader.open(FSDirectory.open(luceneTrueDir));
		IndexSearcher searcher = new IndexSearcher(reader);
		BooleanQuery query = new BooleanQuery();

		// if (!searchRangeMap.isEmpty()) {
		// for (String key : searchRangeMap.keySet()) {
		// LuceneLongRange range = searchRangeMap.get(key);
		// NumericRangeQuery<Long> numRange = NumericRangeQuery.newLongRange(key, range.getMin(), range.getMax(), range.isMinInclusive(), range.isMaxInclusive());
		// query.add(numRange, Occur.MUST);
		// }
		// }
		Map<String, String> keyWordsMap = new HashMap<>();
		keyWordsMap.put("name", "张");
		if (!keyWordsMap.isEmpty()) {
			for (String key : keyWordsMap.keySet()) {
				MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_47, new String[] { key }, new StandardAnalyzer(Version.LUCENE_47));
				Query q1 = parser.parse(keyWordsMap.get(key));
				query.add(q1, Occur.MUST);
			}
		}
		Sort sort = null;
		// if (!sortFieldMap.isEmpty()) {
		// List<SortField> sortFields = new ArrayList<SortField>();
		// for (String key : sortFieldMap.keySet()) {
		// SortField field = new SortField(key, Type.STRING_VAL, sortFieldMap.get(key).isReverse());
		// sortFields.add(field);
		// }
		// sort = new Sort(sortFields.toArray(new SortField[] {}));
		// }
		int count = 100;
		ScoreDoc[] hits = null;
		hits = searcher.search(query, null, count).scoreDocs;
		List<String> result = new ArrayList<String>();
		for (ScoreDoc scoreDoc : hits) {
			Document doc = searcher.doc(scoreDoc.doc);
			result.add(doc.getField("businessId").stringValue());
			System.out.println(doc.getField("businessId").stringValue());
		}
	}

	private static void search2() throws Exception {
		DirectoryReader reader = DirectoryReader.open(FSDirectory.open(luceneDir));
		IndexSearcher searcher = new IndexSearcher(reader);
		TermRangeQuery rangeQuery = TermRangeQuery.newStringRange("startDate", "20120100", "20130100", true, true);
		BooleanQuery query = new BooleanQuery();
		// query.add(rangeQuery, Occur.MUST);

		NumericRangeQuery<Long> numRange = NumericRangeQuery.newLongRange("startDateLong", 0L, 200L, true, true);
		query.add(numRange, Occur.MUST);

		MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_47, new String[] { "name" }, new StandardAnalyzer(Version.LUCENE_47));
		Query q1 = parser.parse("张");
		// query.add(q1, Occur.MUST);

		Sort sort = new Sort(new SortField("id", Type.STRING_VAL, true), new SortField("content", Type.STRING_VAL, false));

		ScoreDoc[] hits = searcher.search(query, null, 10, sort).scoreDocs;
		System.out.println("共有结果：" + hits.length);
		for (ScoreDoc scoreDoc : hits) {
			Document doc = searcher.doc(scoreDoc.doc);
			System.out.println("id:" + doc.getField("id").stringValue() + ",startDate:" + doc.get("startDate") + ",startDateLong:" + doc.get("startDateLong") + ",name:" + doc.getField("name").stringValue() + ",content:" + doc.getField("content").stringValue());
		}
	}

	private static void search() throws Exception {
		DirectoryReader reader = DirectoryReader.open(FSDirectory.open(luceneDir));
		IndexSearcher searcher = new IndexSearcher(reader);
		MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_47, new String[] { "content", "name" }, new StandardAnalyzer(Version.LUCENE_47));
		Map<String, Float> boosts = new HashMap<String, Float>();
		boosts.put("content", 1.0f);
		boosts.put("name", 1.0f);
		// MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_47, new String[] { "content", "name" }, new StandardAnalyzer(Version.LUCENE_47), boosts);
		Query query = parser.parse("是");
		ScoreDoc[] hits = searcher.search(query, null, 10).scoreDocs;
		System.out.println("共有结果：" + hits.length);
		for (ScoreDoc scoreDoc : hits) {
			Document doc = searcher.doc(scoreDoc.doc);
			System.out.println("id:" + doc.getField("id").stringValue() + ",name:" + doc.getField("name").stringValue() + ",content:" + doc.getField("content").stringValue());
		}
	}

	private static void index() throws Exception {
		IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_47, new StandardAnalyzer(Version.LUCENE_47));
		IndexWriter writer = new IndexWriter(new SimpleFSDirectory(luceneDir), conf);
		Document doc0 = new Document();
		doc0.add(new TextField("id", UUID.getUUID(), Store.YES));
		doc0.add(new TextField("name", "张三", Store.YES));
		doc0.add(new TextField("content", "上面的方法很简单，但只能判断是否是中文，但不能判断是否是中文标点", Store.YES));
		doc0.add(new StringField("startDate", "20120101", Store.YES));
		doc0.add(new LongField("startDateLong", 100L, Store.YES));

		Document doc1 = new Document();
		doc1.add(new TextField("id", UUID.getUUID(), Store.YES));
		doc1.add(new TextField("name", "李四", Store.YES));
		doc1.add(new TextField("content", "下面的方法很全面，中文字符标点都可以判断", Store.YES));
		doc1.add(new StringField("startDate", "20120101", Store.YES));
		doc1.add(new LongField("startDateLong", 200L, Store.YES));

		Document doc2 = new Document();
		doc2.add(new TextField("id", UUID.getUUID(), Store.YES));
		doc2.add(new TextField("name", "张三", Store.YES));
		doc2.add(new TextField("content", "做中文信息处理，经常会遇到如何判断一个字是否是中文，或者是否是中文的标点符号等", Store.YES));
		doc2.add(new StringField("startDate", "20120201", Store.YES));
		doc2.add(new LongField("startDateLong", 300L, Store.YES));

		Document doc3 = new Document();
		doc3.add(new TextField("id", UUID.getUUID(), Store.YES));
		doc3.add(new TextField("name", "lucy", Store.YES));
		doc3.add(new TextField("content", "return str.substring(0, 1).toUpperCase() + str.substring(1, str.length()); aa bb cc", Store.YES));
		doc3.add(new StringField("startDate", "20120301", Store.YES));
		doc3.add(new LongField("startDateLong", 400L, Store.YES));

		Document doc4 = new Document();
		doc4.add(new TextField("id", UUID.getUUID(), Store.YES));
		doc4.add(new TextField("name", "lily", Store.YES));
		doc4.add(new TextField("content", "items.add(new Item('2', 'second', 'This is great')); dd dd ee cc", Store.YES));
		doc4.add(new StringField("startDate", "20120401", Store.YES));
		doc4.add(new LongField("startDateLong", 500L, Store.YES));

		Document doc5 = new Document();
		doc5.add(new TextField("id", "20130827140214593277287618268802", Store.YES));
		doc5.add(new TextField("name", "张四", Store.YES));
		doc5.add(new TextField("content", "items.add(new Item('2', 'second', 'This is great')); dd dd ee cc", Store.YES));
		doc5.add(new StringField("startDate", "20120401", Store.YES));
		doc5.add(new LongField("startDateLong", 600L, Store.YES));

		writer.addDocument(doc0);
		writer.addDocument(doc1);
		writer.addDocument(doc2);
		writer.addDocument(doc3);
		writer.addDocument(doc4);
		writer.addDocument(doc5);

		writer.commit();
		int numIndexed = writer.numDocs();
		System.out.println("共有索引：" + numIndexed);
		writer.close();
	}

	private static void update() throws Exception {
		IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_47, new StandardAnalyzer(Version.LUCENE_47));
		IndexWriter writer = new IndexWriter(new SimpleFSDirectory(luceneDir), conf);
		Document doc5 = new Document();
		doc5.add(new TextField("id", "20130827140214593277287618268802", Store.YES));
		doc5.add(new TextField("name", "张雾", Store.YES));
		doc5.add(new TextField("content", "items.add(new Item('2', 'second', 'This is great')); dd dd ee cc", Store.YES));
		doc5.add(new StringField("startDate", "20120401", Store.YES));
		writer.updateDocument(new Term("id", "20130827140214593277287618268802"), doc5);
		writer.commit();
		writer.close();
	}

	private static void delete() throws Exception {
		IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_47, new StandardAnalyzer(Version.LUCENE_47));
		IndexWriter writer = new IndexWriter(new SimpleFSDirectory(luceneDir), conf);

		writer.deleteDocuments(new Term("id", "20130827140214593277287618268802"));
		writer.forceMergeDeletes();
		writer.commit();

		writer.close();
	}
}
