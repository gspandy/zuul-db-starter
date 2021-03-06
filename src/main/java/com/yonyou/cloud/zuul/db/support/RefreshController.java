package com.yonyou.cloud.zuul.db.support;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.cloud.zuul.db.route.StoreProxyRouteLocator;

/**
 * 
 * @author joy
 */
@RestController
public class RefreshController {
	private Logger logger = LoggerFactory.getLogger(StoreProxyRouteLocator.class);
	// @Autowired
	// RouteMapper routeMapper;
	@Autowired
	private ZuulProperties properties;

	@Autowired
	DiscoveryClientRouteLocator locator;

	@RequestMapping("/routes/refresh")
	@ResponseBody
	public Map refresh() {
		((StoreProxyRouteLocator) locator).forceRefresh();

		Map<String, String> map = new HashMap();
		map.put("message", "ok");
		return map;
	}

	@RequestMapping("/routes/list")
	@ResponseBody
	public Map list() {
		return properties.getRoutes();
	}

}
