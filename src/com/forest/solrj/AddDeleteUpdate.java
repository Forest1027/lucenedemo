package com.forest.solrj;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
/*
 * ��ӡ�ɾ�����޸��ĵ�
 * */
public class AddDeleteUpdate {
	@Test
	public void addDocument() throws SolrServerException, IOException {
		//������solr������������ ����SolrServer����
		//����Ϊsolr��������ַ
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/");
		//�����ĵ�����
		SolrInputDocument document = new SolrInputDocument();
		//���ĵ������������
		document.addField("id", "c001");
		document.addField("title", "����ĵ�����");
		document.addField("product_name", "̨��");
		//���ĵ�������ӽ�������
		solrServer.add(document);
		//�ύ
		solrServer.commit();
	}
	
	@Test
	//����idɾ��
	public void deleteDocumentById() throws SolrServerException, IOException {
		//���� ����
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/");
		//����idɾ��
		solrServer.deleteById("c001");
		//�ύ
		solrServer.commit();
	}
	
	@Test
	//���ݲ�ѯɾ��
	public void deleteDocumentByQuery() throws SolrServerException, IOException {
		//��������
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/");
		//���ݲ�ѯɾ��
		solrServer.deleteByQuery("*:*");
		//�ύ
		solrServer.commit();
	}
}
