package com.forest.query;

import java.io.File;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class TestQuery {
	private IndexSearcher getIndexSearcher() throws Exception {
		// ָ���������ŵ�·��
		Directory directory = FSDirectory.open(new File("C:\\Users\\Forest\\Documents\\javatemp"));
		// ����һ��IndexReader����
		IndexReader indexReader = DirectoryReader.open(directory);
		// ����IndexSearcher����
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		return indexSearcher;
	}

	private void printResult(IndexSearcher indexSearcher, Query query) throws Exception {
		// ��ѯ������
		TopDocs topDocs = indexSearcher.search(query, 100);
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		System.out.println("��ѯ����ܼ�¼����" + topDocs.totalHits);
		// ������ѯ���
		for (ScoreDoc scoreDoc : scoreDocs) {
			int docId = scoreDoc.doc;
			// ͨ��id��ѯ�ĵ�����
			Document document = indexSearcher.doc(docId);
			// ȡ����
			System.out.println("name:" + document.get("filename"));
			System.out.println("size:" + document.get("size"));
			System.out.println("content:"+document.get("fileContent"));
			System.out.println("path:" + document.get("filePath"));
			System.out.println();
		}
		// �ر�������
		indexSearcher.getIndexReader().close();
	}

	@Test
	public void testMatchAllDocsQuery() throws Exception {
		IndexSearcher indexSearcher = getIndexSearcher();
		Query query = new MatchAllDocsQuery();
		printResult(indexSearcher, query);
	}

	@Test
	public void testTermQuery() throws Exception {
		IndexSearcher indexSearcher = getIndexSearcher();
		// ������ѯ����
		Query query = new TermQuery(new Term("filename", "mybatis.txt"));
		printResult(indexSearcher, query);
	}

	@Test
	public void testNumericRangeQuery() throws Exception {
		IndexSearcher indexSearcher = getIndexSearcher();
		// ������ѯ
		// ������
		// 1.����
		// 2.��Сֵ
		// 3.���ֵ
		// 4.�Ƿ������Сֵ
		// 5.�Ƿ�������ֵ
		NumericRangeQuery<Long> query = NumericRangeQuery.newLongRange("size", 1000l, 3000l, true, true);
		printResult(indexSearcher, query);
	}

	@Test
	public void testBooleanQuery() throws Exception {
		IndexSearcher indexSearcher = getIndexSearcher();
		// ����������ѯ����
		BooleanQuery query = new BooleanQuery();
		// ����������ѯ����
		Query query1 = new TermQuery(new Term("filename", "mybatis.txt"));
		Query query2 = NumericRangeQuery.newLongRange("size", 1000l, 3000l, true, true);
		// ��ϲ�ѯ����
		query.add(query1, Occur.MUST_NOT);
		query.add(query2, Occur.MUST);
		// ִ�в�ѯ
		printResult(indexSearcher, query);
	}

	@Test
	public void testQueryParser() throws Exception {
		IndexSearcher indexSearcher = getIndexSearcher();
		// ����queryparser����
		// ��һ������Ĭ����������
		// �ڶ����������Ƿ���������
		QueryParser queryParser = new QueryParser("content", new IKAnalyzer());
		Query query = queryParser.parse("Lucene��java������");
		System.out.println(query);
		printResult(indexSearcher, query);
	}
}
