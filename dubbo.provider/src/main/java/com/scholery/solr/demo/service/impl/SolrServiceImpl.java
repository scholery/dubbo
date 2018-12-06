package com.scholery.solr.demo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.dubbo.config.annotation.Service;
import com.scholery.solr.demo.model.Worktime;
import com.scholery.solr.demo.service.SolrService;

@Service
public class SolrServiceImpl implements SolrService {
	static Logger logger = LoggerFactory.getLogger(SolrServiceImpl.class);

	@Value("${solr.url}")
	private String solrUrl;
	private SolrClient client = null;

	@Override
	public List<Worktime> queryWorktime(String q, int page, int start) {
		if (null == client) {
			client = new HttpSolrClient.Builder(solrUrl).withConnectionTimeout(10000).withSocketTimeout(60000).build();
		}
		final SolrQuery query = new SolrQuery(!StringUtils.isEmpty(q) ? q : "*");
		query.set("df", "keywords");
		query.setSort("work_date", ORDER.desc);
		List<SortClause> ll = new ArrayList<SortClause>();
		ll.add(SortClause.create("work_date", ORDER.desc));
		ll.add(SortClause.create("id", ORDER.desc));
		query.setSorts(ll);
		query.setRows(page);
		query.setStart(start);
		query.setHighlight(true);
		query.addHighlightField("emp_code,emp_name,prj_name,work_date");
		query.setHighlightSimplePre("<span style='color:red'>");
		query.setHighlightSimplePost("</span>");

		List<Worktime> ws = new ArrayList<>();
		try {
			QueryResponse response = client.query("worktime", query);
			final SolrDocumentList documents = response.getResults();
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			Map<String, List<String>> map;
			List<String> list = null;
			String hh = null;
			Worktime w;
			for (SolrDocument document : documents) {
				w = new Worktime();
				w.setId(NumberUtils.toInt(document.getFieldValue("id").toString()));
				w.setEmpId(NumberUtils.toInt(document.getFieldValue("emp_id").toString()));
				w.setEmpCode(document.getFieldValue("emp_code").toString());
				w.setEmpName(document.getFieldValue("emp_name").toString());
				w.setPrjId(NumberUtils.toInt(document.getFieldValue("prj_id").toString()));
				w.setPrjName(document.getFieldValue("prj_name").toString());
				w.setActId(NumberUtils.toInt(document.getFieldValue("act_id").toString()));
				w.setActName(document.getFieldValue("act_name").toString());
				w.setWorkHours(NumberUtils.toFloat(document.getFieldValue("work_hours").toString()));
				w.setOverWorkHours(NumberUtils.toFloat(document.getFieldValue("over_work_hours").toString()));
				w.setWorkDate((Date) document.getFieldValue("work_date"));
				w.setWorkDateStr(DateFormatUtils.format(w.getWorkDate().getTime(), "YYYY-MM-dd"));

				map = highlighting.get(document.get("id").toString());
				if (null != map && map.containsKey("work_date")) {
					list = map.get("work_date");
					w.setWorkDateStr(list.get(0));
				}
				if (null != map && map.containsKey("emp_name")) {
					list = map.get("emp_name");
					w.setEmpName(list.get(0));
				}
				if (null != map && map.containsKey("emp_code")) {
					list = map.get("emp_code");
					w.setEmpCode(list.get(0));
				}
				if (null != map && map.containsKey("prj_name")) {
					list = map.get("prj_name");
					w.setPrjName(list.get(0));
				}
				logger.debug("document:{},{}", document.toString(), hh);
				ws.add(w);
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ws;
	}

}
