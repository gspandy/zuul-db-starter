package com.yonyou.cloud.zuul.db.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.yonyou.cloud.zuul.db.route.StoreProxyRouteLocator;

@Configuration
@EnableScheduling
public class RoutesRefreshTask {
	private Logger logger = LoggerFactory.getLogger(StoreProxyRouteLocator.class);

	@Autowired
	DiscoveryClientRouteLocator locator;

	@Scheduled(cron="0 0/5 * * * ?")
	public void refreshRoutes() {
		logger.debug("---- refreshing routes  start----");
		((StoreProxyRouteLocator) locator).forceRefresh();
		logger.debug("---- refreshing routes  end----");
	}

}
