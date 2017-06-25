package cn.itcast.solr;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

public class SolrQueryTest {
	
	@Test
	public void query() throws Exception{
		//创建连接
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/");
		//创建一个query对象
		SolrQuery query = new SolrQuery();
		//设置查询条件
		//关键词
//		query.set("q", "product_name:钻石");
		query.setQuery("钻石"); 
		//fq  过滤条件
		query.set("fq", "product_price:[* TO 9]");
		//价格降   排序
		query.setSort("product_price", ORDER.asc);
		//分页
		query.setStart(0);
		query.setRows(5);
		//需要显示的字段
		query.set("f1", "id,product_name");
		//默认查询的内容
		query.set("df", "product_name");
		
		//高亮
		//1打开高亮开关
		query.setHighlight(true);
		//2设置高亮的域
		query.addHighlightField("product_name");
		//3设置高亮的前缀  后缀
		query.setHighlightSimplePre("<font color=red>");
		query.setHighlightSimplePost("</font>");
		//执行查询
		QueryResponse response = solrServer.query(query);
		//高亮
		Map<String,Map<String,List<String>>> highlighting = response.getHighlighting();
		//第一个Map   K id  V Map
		//第二个Map   K 域名  V 域值  （List  多值 )
		//List  目前只有一个值  list.get(0)
		//结果集
		SolrDocumentList docs = response.getResults();
		//总条数
		long numFound = docs.getNumFound();
		System.out.println("总条数"+numFound);
		
		for (SolrDocument doc : docs) {
			System.out.println(doc.get("id"));
			System.out.println(doc.get("product_name"));
			System.out.println("------下面是高亮的-----");
			Map<String, List<String>> map = highlighting.get(doc.get("id"));
			List<String> list = map.get("product_name");
			System.out.println("高亮的名称：" + list.get(0));
		}
		
		
		
		
		
		
	}

}
