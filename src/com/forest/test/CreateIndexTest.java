package com.forest.test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

/*��һ��������һ��java���̣�������jar����
�ڶ���������һ��indexwriter����
1��ָ��������Ĵ��λ��Directory����
2��ָ��һ�������������ĵ����ݽ��з�����
�ڶ���������document����
������������field���󣬽�field��ӵ�document�����С�
���Ĳ���ʹ��indexwriter����document����д�������⣬�˹��̽�����������������������document����д�������⡣
���岽���ر�IndexWriter����*/

public class CreateIndexTest {
	@Test
	public void test() throws IOException {
		// ��һ��������һ��indexwriter����
		// 1��ָ��������Ĵ��λ��Directory����
		Directory directory = FSDirectory.open(new File("C:\\Users\\Forest\\Documents\\javatemp"));
		// �����⻹���Դ�ŵ��ڴ���
		// Directory directory = new RAMDirectory();
		// 2��ָ��һ�������������ĵ����ݽ��з�����
		Analyzer analyzer = new StandardAnalyzer();
		// ����indexwriterCofig����
		// ��һ�������� Lucene�İ汾��Ϣ������ѡ���Ӧ��lucene�汾Ҳ����ʹ��LATEST
		// �ڶ�������������������
		IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);
		// ����indexwriter����
		IndexWriter indexWriter = new IndexWriter(directory, config);
		// ԭʼ�ĵ���·��
		File dir = new File("C:\\Users\\Forest\\Documents\\JAVA�ĵ�\\�̲���\\ssm\\lucene&solr\\day01\\����\\searchsource");
		// ��ȡԭʼ�ļ����ݣ�����field����
		for (File f : dir.listFiles()) {
			// �ļ���
			String fileName = f.getName();
			// �ļ�����
			String fileContent = FileUtils.readFileToString(f);
			// �ļ�·��
			String filePath = f.getPath();
			// �ļ��Ĵ�С
			long fileSize = FileUtils.sizeOf(f);
			// �����ļ�����Field
			// ��һ���������������
			// �ڶ����������������
			// �������������Ƿ�洢
			Field fileNameField = new TextField("filename", fileName, Store.YES);
			// �ļ�������
			Field fileContentField = new TextField("fileContent", fileContent, Store.YES);
			// �ļ�·���򣨲���������������ֻ�洢��
			Field filePathField = new StoredField("filePath", filePath);
			// �ļ���С��
			Field fileSizeField = new LongField("size", fileSize, Store.YES);
			// �ڶ���������document����
			Document document = new Document();
			// ����������field��ӵ�document�����С�
			document.add(fileNameField);
			document.add(fileContentField);
			document.add(filePathField);
			document.add(fileSizeField);
			// ���Ĳ���ʹ��indexwriter����document����д�������⣬�˹��̽�����������������������document����д�������⡣
			indexWriter.addDocument(document);
		}
		// ���岽���ر�IndexWriter����
		indexWriter.close();
	}
}
