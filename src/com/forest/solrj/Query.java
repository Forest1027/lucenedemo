package com.forest.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

/*
 * 本类主要编写了简单查询和复杂查询。
 * 其中复杂查询应用范围很广，主要是根据solr服务器查询页面一个个对应的java api。值得收藏
 * */
public class Query {
	@Test
	public void complexQuery() throws SolrServerException {
		// 创建连接
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");
		// 创建查询对象
		SolrQuery solrQuery = new SolrQuery();
		// 设置查询条件
		solrQuery.setQuery("钻石");
		// 过滤条件
		solrQuery.setFilterQueries("product_catalog_name:幽默杂货");
		// 排序条件
		solrQuery.setSort("product_price", ORDER.asc);
		// 分页处理
		solrQuery.setStart(0);
		solrQuery.setRows(10);
		// 结果中域的列表
		solrQuery.setFields("id", "product_name", "product_price", "product_catalog_name", "product_picture");
		// 设置默认
		solrQuery.set("df", "product_keywords");

		// 设置高亮
		solrQuery.setHighlight(true);
		// 高亮显示的域
		solrQuery.addHighlightField("product_name");
		// 高亮显示的前缀
		solrQuery.setHighlightSimplePre("<em>");
		// 高亮显示的后缀
		solrQuery.setHighlightSimplePost("</em>");

		// 执行查询
		QueryResponse response = solrServer.query(solrQuery);
		// 取查询结果
		SolrDocumentList solrDocumentList = response.getResults();
		System.out.println("查询总量：" + solrDocumentList.getNumFound());
		// 遍历查询结果
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("product_name"));
			System.out.println(solrDocument.get("product_price"));
			System.out.println(solrDocument.get("product_catalog_name"));
			System.out.println(solrDocument.get("product_picture"));
			System.out.println("-----------------");
		}
	}

	@Test
	public void simpleQuery() throws SolrServerException {
		// 创建查询链接
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");
		// 创建查询对象
		SolrQuery solrQuery = new SolrQuery();
		// 设置查询条件
		solrQuery.setQuery("*:*");
		// 执行查询
		QueryResponse response = solrServer.query(solrQuery);
		// 取出查询结果
		SolrDocumentList results = response.getResults();
		// 得出查询总量
		System.out.println("查询到的总量是：" + results.getNumFound());
		// 遍历查询结果
		for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("product_name"));
			System.out.println(solrDocument.get("product_price"));
			System.out.println(solrDocument.get("product_catalog_name"));
			System.out.println(solrDocument.get("product_picture"));
			System.out.println("-----------------");
		}
	}
}
