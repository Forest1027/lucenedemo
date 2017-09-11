package com.forest.solrj;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
/*
 * 添加、删除、修改文档
 * */
public class AddDeleteUpdate {
	@Test
	public void addDocument() throws SolrServerException, IOException {
		//创建与solr服务器的链接 创建SolrServer对象
		//参数为solr服务器地址
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/");
		//创建文档对象
		SolrInputDocument document = new SolrInputDocument();
		//向文档对象中添加域
		document.addField("id", "c001");
		document.addField("title", "添加文档测试");
		document.addField("product_name", "台灯");
		//将文档对象添加进索引库
		solrServer.add(document);
		//提交
		solrServer.commit();
	}
	
	@Test
	//根据id删除
	public void deleteDocumentById() throws SolrServerException, IOException {
		//创建 链接
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/");
		//根据id删除
		solrServer.deleteById("c001");
		//提交
		solrServer.commit();
	}
	
	@Test
	//根据查询删除
	public void deleteDocumentByQuery() throws SolrServerException, IOException {
		//创建连接
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/");
		//根据查询删除
		solrServer.deleteByQuery("*:*");
		//提交
		solrServer.commit();
	}
}
