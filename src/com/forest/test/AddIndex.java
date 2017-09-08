package com.forest.test;
/*向索引库中添加document对象。
第一步：先创建一个indexwriter对象
第二步：创建一个document对象
第三步：把document对象写入索引库
第四步：关闭indexwriter。
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
		// 索引库存放路径
		Directory directory = FSDirectory.open(new File("C:\\Users\\Forest\\Documents\\javatemp"));

		IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, new IKAnalyzer());
		// 创建一个indexwriter对象
		IndexWriter indexWriter = new IndexWriter(directory, config);
		// 创建一个Document对象
		Document document = new Document();
		// 向document对象中添加域。
		// 不同的document可以有不同的域，同一个document可以有相同的域。
		document.add(new TextField("filename", "新添加的文档", Store.YES));
		document.add(new TextField("content", "新添加的文档的内容", Store.NO));
		document.add(new TextField("content", "新添加的文档的内容第二个content", Store.YES));
		document.add(new TextField("content1", "新添加的文档的内容要能看到", Store.YES));
		// 添加文档到索引库
		indexWriter.addDocument(document);
		// 关闭indexwriter
		indexWriter.close();

	}
}
