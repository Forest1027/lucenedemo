package com.forest.test;
/*��һ��������һ��Directory����Ҳ�����������ŵ�λ�á�
�ڶ���������һ��indexReader������Ҫָ��Directory����
������������һ��indexsearcher������Ҫָ��IndexReader����
���Ĳ�������һ��TermQuery����ָ����ѯ����Ͳ�ѯ�Ĺؼ��ʡ�
���岽��ִ�в�ѯ��
�����������ز�ѯ�����������ѯ����������
���߲����ر�IndexReader����*/

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

public class QueryIndexTest {
	@Test
	public void test() throws IOException {
		// ��һ��������һ��Directory����Ҳ�����������ŵ�λ�á�
		Directory directory = FSDirectory.open(new File("C:\\Users\\Forest\\Documents\\javatemp"));
		// �ڶ���������һ��indexReader������Ҫָ��Directory����
		IndexReader indexReader = DirectoryReader.open(directory);
		// ������������һ��indexsearcher������Ҫָ��IndexReader����
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		// ���Ĳ�������һ��TermQuery����ָ����ѯ����Ͳ�ѯ�Ĺؼ��ʡ�
		Query query = new TermQuery(new Term("filename","web"));
		// ���岽��ִ�в�ѯ��
		//��һ�������ǲ�ѯ���󣬵ڶ��������ǲ�ѯ������ص����ֵ
		TopDocs topDocs = indexSearcher.search(query, 10);
		//��ѯ�����������
		System.out.println("��ѯ��������"+topDocs.totalHits);
		// �����������ز�ѯ�����������ѯ����������
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			//scoreDoc.doc���Ծ���document�����id
			//����document��id�ҵ�document����
			Document document = indexSearcher.doc(scoreDoc.doc);
			System.out.println(document.get("filename"));
			System.out.println(document.get("filePath"));
			System.out.println(document.get("size"));
		}
		// ���߲����ر�IndexReader����
		indexReader.close();
	}
}
