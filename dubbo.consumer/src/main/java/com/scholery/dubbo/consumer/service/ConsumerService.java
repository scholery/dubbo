package com.scholery.dubbo.consumer.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.scholery.solr.demo.model.Worktime;
import com.scholery.solr.demo.service.SolrService;

@Component
public class ConsumerService {

	@Reference
	SolrService solrService;
	
	public List<Worktime> queryWorktime(String q, int page, int start){
		return solrService.queryWorktime(q, page, start);
	}
}
