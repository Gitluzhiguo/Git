package cn.itcast.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/*
 * 进行增删改查
 * 
 * 修改 操作  如果ID相同  则替换以前的  存入现在的
 */


public class SolrTest {

	
	//曾加操作
	@Test
	public void addSolr() throws Exception, IOException{
		//连接索引库
		String baseURL ="http://localhost:8080/solr/";
		SolrServer solrserver = new HttpSolrServer(baseURL);
		
		
		//进行添加操作
		SolrInputDocument doc = new SolrInputDocument();
		doc.setField("id", 3);
		doc.setField("name", "鸢十三");
		solrserver.add(doc);
		solrserver.commit();

	}
	
	//删除操作
	@Test
	public void deletSolr() throws Exception, IOException{
		String baseURL = "http://localhost:8080/solr/";
		SolrServer solrserver = new HttpSolrServer(baseURL);
		
		solrserver.deleteById("3");
		solrserver.commit();
	}
	
	//查询操作
	@Test
	public void query() throws Exception{
		String baseURL = "http://localhost:8080/solr/";
		SolrServer solrserver = new HttpSolrServer(baseURL);
		
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.set("q", "id:1");
		//执行查询
		QueryResponse response = solrserver.query(solrQuery);
		
		SolrDocumentList document = response.getResults();
		long numFound = document.getNumFound();
		System.out.println("总条数"+numFound);
		for (SolrDocument doc : document) {
			System.out.println("ID"+doc.get("id"));
			System.out.println("name"+doc.get("name"));
		}
	}
	
	
	
	
	
	
}
