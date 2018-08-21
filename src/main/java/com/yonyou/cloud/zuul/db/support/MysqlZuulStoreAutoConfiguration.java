package com.yonyou.cloud.zuul.db.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yonyou.cloud.zuul.db.route.StoreProxyRouteLocator;
import com.yonyou.cloud.zuul.db.store.MysqlZuulRouteStore;
import com.yonyou.cloud.zuul.db.store.ZuulRouteStore;

/**
 * 
 * @author joy
 */
@Configuration
@ConditionalOnProperty(value = "zuul.store.mysql.enabled", havingValue = "true", matchIfMissing = false)
public class MysqlZuulStoreAutoConfiguration {
	private Logger logger = LoggerFactory.getLogger(StoreProxyRouteLocator.class);

	@Bean
	@ConditionalOnMissingBean
	public ZuulRouteStore mysqlZuulRouteStore() {
		logger.info("--MysqlZuulStoreAutoConfiguration.mysqlZuulRouteStore");
		return new MysqlZuulRouteStore();
	}
}
