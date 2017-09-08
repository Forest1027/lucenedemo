package com.forest.test;
/*第一步：创建一个Directory对象，也就是索引库存放的位置。
第二步：创建一个indexReader对象，需要指定Directory对象。
第三步：创建一个indexsearcher对象，需要指定IndexReader对象
第四步：创建一个TermQuery对象，指定查询的域和查询的关键词。
第五步：执行查询。
第六步：返回查询结果。遍历查询结果并输出。
第七步：关闭IndexReader对象*/

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
		// 第一步：创建一个Directory对象，也就是索引库存放的位置。
		Directory directory = FSDirectory.open(new File("C:\\Users\\Forest\\Documents\\javatemp"));
		// 第二步：创建一个indexReader对象，需要指定Directory对象。
		IndexReader indexReader = DirectoryReader.open(directory);
		// 第三步：创建一个indexsearcher对象，需要指定IndexReader对象
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		// 第四步：创建一个TermQuery对象，指定查询的域和查询的关键词。
		Query query = new TermQuery(new Term("filename","web"));
		// 第五步：执行查询。
		//第一个参数是查询对象，第二个参数是查询结果返回的最大值
		TopDocs topDocs = indexSearcher.search(query, 10);
		//查询结果的总条数
		System.out.println("查询总条数："+topDocs.totalHits);
		// 第六步：返回查询结果。遍历查询结果并输出。
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			//scoreDoc.doc属性就是document对象的id
			//根据document的id找到document对象
			Document document = indexSearcher.doc(scoreDoc.doc);
			System.out.println(document.get("filename"));
			System.out.println(document.get("filePath"));
			System.out.println(document.get("size"));
		}
		// 第七步：关闭IndexReader对象
		indexReader.close();
	}
}
