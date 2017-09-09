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
		// 指定索引库存放的路径
		Directory directory = FSDirectory.open(new File("C:\\Users\\Forest\\Documents\\javatemp"));
		// 创建一个IndexReader对象
		IndexReader indexReader = DirectoryReader.open(directory);
		// 创建IndexSearcher对象
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		return indexSearcher;
	}

	private void printResult(IndexSearcher indexSearcher, Query query) throws Exception {
		// 查询索引库
		TopDocs topDocs = indexSearcher.search(query, 100);
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		System.out.println("查询结果总记录数：" + topDocs.totalHits);
		// 遍历查询结果
		for (ScoreDoc scoreDoc : scoreDocs) {
			int docId = scoreDoc.doc;
			// 通过id查询文档对象
			Document document = indexSearcher.doc(docId);
			// 取属性
			System.out.println("name:" + document.get("filename"));
			System.out.println("size:" + document.get("size"));
			System.out.println("content:"+document.get("fileContent"));
			System.out.println("path:" + document.get("filePath"));
			System.out.println();
		}
		// 关闭索引库
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
		// 创建查询对象
		Query query = new TermQuery(new Term("filename", "mybatis.txt"));
		printResult(indexSearcher, query);
	}

	@Test
	public void testNumericRangeQuery() throws Exception {
		IndexSearcher indexSearcher = getIndexSearcher();
		// 创建查询
		// 参数：
		// 1.域名
		// 2.最小值
		// 3.最大值
		// 4.是否包含最小值
		// 5.是否包含最大值
		NumericRangeQuery<Long> query = NumericRangeQuery.newLongRange("size", 1000l, 3000l, true, true);
		printResult(indexSearcher, query);
	}

	@Test
	public void testBooleanQuery() throws Exception {
		IndexSearcher indexSearcher = getIndexSearcher();
		// 创建布尔查询对象
		BooleanQuery query = new BooleanQuery();
		// 创建两个查询条件
		Query query1 = new TermQuery(new Term("filename", "mybatis.txt"));
		Query query2 = NumericRangeQuery.newLongRange("size", 1000l, 3000l, true, true);
		// 组合查询条件
		query.add(query1, Occur.MUST_NOT);
		query.add(query2, Occur.MUST);
		// 执行查询
		printResult(indexSearcher, query);
	}

	@Test
	public void testQueryParser() throws Exception {
		IndexSearcher indexSearcher = getIndexSearcher();
		// 创建queryparser对象
		// 第一个参数默认搜索的域
		// 第二个参数就是分析器对象
		QueryParser queryParser = new QueryParser("content", new IKAnalyzer());
		Query query = queryParser.parse("Lucene是java开发的");
		System.out.println(query);
		printResult(indexSearcher, query);
	}
}
