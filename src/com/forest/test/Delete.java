package com.forest.test;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class Delete {
	public IndexWriter getIndexWriter() throws Exception {
		Directory directory = FSDirectory.open(new File("C:\\Users\\Forest\\Documents\\javatemp"));
		Analyzer analyzer = new IKAnalyzer();
		// ����1��lucene�İ汾�ţ��ڶ�������������������
		IndexWriterConfig conf = new IndexWriterConfig(Version.LATEST, analyzer);
		IndexWriter indexWriter = new IndexWriter(directory, conf);
		return indexWriter;
	}

	@Test
	public void deleteAll() throws Exception {
		// �˷�����
		IndexWriter indexWriter = this.getIndexWriter();
		// ɾ��ȫ������
		indexWriter.deleteAll();
		// �ر�indexwriter
		indexWriter.close();

	}

	// ���ݲ�ѯ����ɾ������
	@Test
	public void deleteIndexByQuery() throws Exception {
		IndexWriter indexWriter = getIndexWriter();
		// ����һ����ѯ����
		Query query = new TermQuery(new Term("filename", "apache"));
		// ���ݲ�ѯ����ɾ��
		indexWriter.deleteDocuments(query);
		// �ر�indexwriter
		indexWriter.close();
	}

	// �޸�������
	@Test
	public void updateIndex() throws Exception {
		IndexWriter indexWriter = getIndexWriter();
		// ����һ��Document����
		Document document = new Document();
		// ��document�����������
		// ��ͬ��document�����в�ͬ����ͬһ��document��������ͬ����
		document.add(new TextField("filename", "Ҫ���µ��ĵ�", Store.YES));
		document.add(new TextField("content",
				"2013��11��18�� - Lucene ��� Lucene ��һ������ Java ��ȫ����Ϣ�������߰�,������һ������������Ӧ�ó���,����Ϊ���Ӧ�ó����ṩ�������������ܡ�", Store.YES));
		indexWriter.updateDocument(new Term("content", "java"), document);
		// �ر�indexWriter
		indexWriter.close();
	}

}
