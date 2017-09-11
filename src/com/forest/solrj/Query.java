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
 * ������Ҫ��д�˼򵥲�ѯ�͸��Ӳ�ѯ��
 * ���и��Ӳ�ѯӦ�÷�Χ�ܹ㣬��Ҫ�Ǹ���solr��������ѯҳ��һ������Ӧ��java api��ֵ���ղ�
 * */
public class Query {
	@Test
	public void complexQuery() throws SolrServerException {
		// ��������
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");
		// ������ѯ����
		SolrQuery solrQuery = new SolrQuery();
		// ���ò�ѯ����
		solrQuery.setQuery("��ʯ");
		// ��������
		solrQuery.setFilterQueries("product_catalog_name:��Ĭ�ӻ�");
		// ��������
		solrQuery.setSort("product_price", ORDER.asc);
		// ��ҳ����
		solrQuery.setStart(0);
		solrQuery.setRows(10);
		// ���������б�
		solrQuery.setFields("id", "product_name", "product_price", "product_catalog_name", "product_picture");
		// ����Ĭ��
		solrQuery.set("df", "product_keywords");

		// ���ø���
		solrQuery.setHighlight(true);
		// ������ʾ����
		solrQuery.addHighlightField("product_name");
		// ������ʾ��ǰ׺
		solrQuery.setHighlightSimplePre("<em>");
		// ������ʾ�ĺ�׺
		solrQuery.setHighlightSimplePost("</em>");

		// ִ�в�ѯ
		QueryResponse response = solrServer.query(solrQuery);
		// ȡ��ѯ���
		SolrDocumentList solrDocumentList = response.getResults();
		System.out.println("��ѯ������" + solrDocumentList.getNumFound());
		// ������ѯ���
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
		// ������ѯ����
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");
		// ������ѯ����
		SolrQuery solrQuery = new SolrQuery();
		// ���ò�ѯ����
		solrQuery.setQuery("*:*");
		// ִ�в�ѯ
		QueryResponse response = solrServer.query(solrQuery);
		// ȡ����ѯ���
		SolrDocumentList results = response.getResults();
		// �ó���ѯ����
		System.out.println("��ѯ���������ǣ�" + results.getNumFound());
		// ������ѯ���
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
