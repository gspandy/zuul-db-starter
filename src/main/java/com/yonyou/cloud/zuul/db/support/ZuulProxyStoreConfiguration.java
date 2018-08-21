package com.yonyou.cloud.zuul.db.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.ZuulProxyAutoConfiguration;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import org.springframework.context.annotation.Configuration;

import com.yonyou.cloud.zuul.db.route.StoreProxyRouteLocator;
import com.yonyou.cloud.zuul.db.store.ZuulRouteStore;

/**
 * 
 * @author joy
 */
@Configuration
public class ZuulProxyStoreConfiguration extends ZuulProxyAutoConfiguration {
	private Logger logger = LoggerFactory.getLogger(StoreProxyRouteLocator.class);

	@Autowired
	private ZuulRouteStore zuulRouteStore;

	@Autowired
	private DiscoveryClient discovery;

	@Autowired
	private ZuulProperties zuulProperties;

	@Autowired
	private ServerProperties server;

	public ZuulProxyStoreConfiguration() {
		logger.info("--ZuulProxyStoreConfiguration()");
	}

	@Override
	public DiscoveryClientRouteLocator discoveryRouteLocator() {
		logger.info("--ZuulProxyStoreConfiguration.routeLocator");
		// return new StoreProxyRouteLocator(server.getServletPath(), discovery, zuulProperties, zuulRouteStore);
		// 获取path的方式变更 springboot2.0
		return new StoreProxyRouteLocator(server.getServlet().getPath(), discovery, zuulProperties, zuulRouteStore);
	}
}
