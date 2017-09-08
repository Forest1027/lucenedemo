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

/*第一步：创建一个java工程，并导入jar包。
第二步：创建一个indexwriter对象。
1）指定索引库的存放位置Directory对象
2）指定一个分析器，对文档内容进行分析。
第二步：创建document对象。
第三步：创建field对象，将field添加到document对象中。
第四步：使用indexwriter对象将document对象写入索引库，此过程进行索引创建。并将索引和document对象写入索引库。
第五步：关闭IndexWriter对象。*/

public class CreateIndexTest {
	@Test
	public void test() throws IOException {
		// 第一步：创建一个indexwriter对象。
		// 1）指定索引库的存放位置Directory对象
		Directory directory = FSDirectory.open(new File("C:\\Users\\Forest\\Documents\\javatemp"));
		// 索引库还可以存放到内存中
		// Directory directory = new RAMDirectory();
		// 2）指定一个分析器，对文档内容进行分析。
		Analyzer analyzer = new StandardAnalyzer();
		// 创建indexwriterCofig对象
		// 第一个参数： Lucene的版本信息，可以选择对应的lucene版本也可以使用LATEST
		// 第二根参数：分析器对象
		IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);
		// 创建indexwriter对象
		IndexWriter indexWriter = new IndexWriter(directory, config);
		// 原始文档的路径
		File dir = new File("C:\\Users\\Forest\\Documents\\JAVA文档\\教材们\\ssm\\lucene&solr\\day01\\资料\\searchsource");
		// 读取原始文件内容，创建field对象
		for (File f : dir.listFiles()) {
			// 文件名
			String fileName = f.getName();
			// 文件内容
			String fileContent = FileUtils.readFileToString(f);
			// 文件路径
			String filePath = f.getPath();
			// 文件的大小
			long fileSize = FileUtils.sizeOf(f);
			// 创建文件名域Field
			// 第一个参数：域的名称
			// 第二个参数：域的内容
			// 第三个参数：是否存储
			Field fileNameField = new TextField("filename", fileName, Store.YES);
			// 文件内容域
			Field fileContentField = new TextField("fileContent", fileContent, Store.YES);
			// 文件路径域（不分析、不索引、只存储）
			Field filePathField = new StoredField("filePath", filePath);
			// 文件大小域
			Field fileSizeField = new LongField("size", fileSize, Store.YES);
			// 第二步：创建document对象。
			Document document = new Document();
			// 第三步：将field添加到document对象中。
			document.add(fileNameField);
			document.add(fileContentField);
			document.add(filePathField);
			document.add(fileSizeField);
			// 第四步：使用indexwriter对象将document对象写入索引库，此过程进行索引创建。并将索引和document对象写入索引库。
			indexWriter.addDocument(document);
		}
		// 第五步：关闭IndexWriter对象。
		indexWriter.close();
	}
}
