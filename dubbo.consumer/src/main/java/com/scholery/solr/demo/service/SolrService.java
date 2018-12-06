package com.scholery.solr.demo.service;

import java.util.List;

import com.scholery.solr.demo.model.Worktime;

public interface SolrService {

	public List<Worktime> queryWorktime(String q, int page, int start);
	
}
