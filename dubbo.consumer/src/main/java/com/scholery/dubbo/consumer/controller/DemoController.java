package com.scholery.dubbo.consumer.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;
import com.scholery.solr.demo.model.Worktime;
import com.scholery.solr.demo.service.SolrService;

@Controller
@RequestMapping(value = "demo")
public class DemoController {
	static Logger logger = LoggerFactory.getLogger(DemoController.class);

	/*@Autowired
	ConsumerService consumerService;*/
	@Reference
	SolrService solrService;

	@RequestMapping(value = { "", "/" })
	@ResponseBody
	public String test(Model model) {
		return "test";
	}

	@RequestMapping(value = "/worktimeQ")
	public ModelAndView worktimeQ() {
		return new ModelAndView("worktimeQ");
	}

	@ResponseBody
	@RequestMapping(value = "/worktimeQ/q")
	public List<Worktime> solrSearch(@RequestParam(name = "q", required = false) String q,
			@RequestParam(name = "page") int page, @RequestParam(name = "page", required = false) int start) {

		return solrService.queryWorktime(q, page, start);
	}

}