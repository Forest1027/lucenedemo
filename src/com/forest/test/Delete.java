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
		// 参数1：lucene的版本号，第二个参数：分析器对象
		IndexWriterConfig conf = new IndexWriterConfig(Version.LATEST, analyzer);
		IndexWriter indexWriter = new IndexWriter(directory, conf);
		return indexWriter;
	}

	@Test
	public void deleteAll() throws Exception {
		// 此法慎用
		IndexWriter indexWriter = this.getIndexWriter();
		// 删除全部索引
		indexWriter.deleteAll();
		// 关闭indexwriter
		indexWriter.close();

	}

	// 根据查询条件删除索引
	@Test
	public void deleteIndexByQuery() throws Exception {
		IndexWriter indexWriter = getIndexWriter();
		// 创建一个查询条件
		Query query = new TermQuery(new Term("filename", "apache"));
		// 根据查询条件删除
		indexWriter.deleteDocuments(query);
		// 关闭indexwriter
		indexWriter.close();
	}

	// 修改索引库
	@Test
	public void updateIndex() throws Exception {
		IndexWriter indexWriter = getIndexWriter();
		// 创建一个Document对象
		Document document = new Document();
		// 向document对象中添加域。
		// 不同的document可以有不同的域，同一个document可以有相同的域。
		document.add(new TextField("filename", "要更新的文档", Store.YES));
		document.add(new TextField("content",
				"2013年11月18日 - Lucene 简介 Lucene 是一个基于 Java 的全文信息检索工具包,它不是一个完整的搜索应用程序,而是为你的应用程序提供索引和搜索功能。", Store.YES));
		indexWriter.updateDocument(new Term("content", "java"), document);
		// 关闭indexWriter
		indexWriter.close();
	}

}
