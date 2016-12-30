package com.machine;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SolrTest {

	private SolrClient solr = null;

	@Before
	public void init() {
		// solr = new
		// HttpSolrClient("http://121.41.118.246:8983/solr/activity");

		solr = new HttpSolrClient("http://112.124.18.27:8983/solr/core0");
	}

	@Test
	public void add() throws SolrServerException, IOException {
		for (int i = 1; i <= 1000; i++) {
			SolrInputDocument doc = new SolrInputDocument();
			doc.addField("id", i);
			doc.addField("description", "Solr Input Document");
			doc.addField("location", "杭州市");
			doc.addField("firstTime", "大事记");
			solr.add(doc);
			if (i % 10 == 0) {
				solr.commit();
			}
		}
		solr.commit();
	}

	@Test
	public void query() throws SolrServerException, IOException {
		SolrQuery params = new SolrQuery("description:Solr");

		QueryResponse response = solr.query(params);

		// 输出查询结果集
		SolrDocumentList list = response.getResults();
		System.out.println("query result nums: " + list.getNumFound());
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}

	}

	@Test
	public void delete() throws SolrServerException, IOException {
		solr.deleteByQuery("*:*");
		solr.commit();
	}

	@Test
	public void optimize() throws SolrServerException, IOException {
		this.solr.commit();
		this.solr.optimize();
	}

	@After
	public void close() throws IOException {
		solr.close();
	}
}
