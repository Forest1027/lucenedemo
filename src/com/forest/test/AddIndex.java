package com.forest.test;
/*�������������document����
��һ�����ȴ���һ��indexwriter����
�ڶ���������һ��document����
����������document����д��������
���Ĳ����ر�indexwriter��
*/

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class AddIndex {
	@Test
	public void addDucument() throws IOException {
		// ��������·��
		Directory directory = FSDirectory.open(new File("C:\\Users\\Forest\\Documents\\javatemp"));

		IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, new IKAnalyzer());
		// ����һ��indexwriter����
		IndexWriter indexWriter = new IndexWriter(directory, config);
		// ����һ��Document����
		Document document = new Document();
		// ��document�����������
		// ��ͬ��document�����в�ͬ����ͬһ��document��������ͬ����
		document.add(new TextField("filename", "����ӵ��ĵ�", Store.YES));
		document.add(new TextField("content", "����ӵ��ĵ�������", Store.NO));
		document.add(new TextField("content", "����ӵ��ĵ������ݵڶ���content", Store.YES));
		document.add(new TextField("content1", "����ӵ��ĵ�������Ҫ�ܿ���", Store.YES));
		// ����ĵ���������
		indexWriter.addDocument(document);
		// �ر�indexwriter
		indexWriter.close();

	}
}
