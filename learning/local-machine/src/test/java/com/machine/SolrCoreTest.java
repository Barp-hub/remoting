package com.machine;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SolrCoreTest {
	private SolrClient solr = null;

	@Before
	public void init() {
		solr = new HttpSolrClient("http://121.41.118.246:8983/solr/core1");
	}

	@Test
	public void add() throws SolrServerException, IOException {

		List<SolrItem> list = new LinkedList<SolrItem>();
		for (int i = 0; i < 10000; i++) {
			SolrItem item = new SolrItem();
			item.setId(String.valueOf(i));
			item.setKeywords("测试");
			item.setTypeName("测试");
			list.add(item);
		}
		solr.addBeans(list);

		solr.commit();
		solr.optimize();

	}

	@Test
	public void delete() throws SolrServerException, IOException {
		solr.deleteByQuery("*:*");
		solr.commit();
	}

	@Test
	public void commit() throws SolrServerException, IOException {
		solr.commit();
	}

	@Test
	public void optimize() throws SolrServerException, IOException {
		this.solr.optimize();
	}

	@After
	public void close() throws IOException {
		solr.close();
	}

}
