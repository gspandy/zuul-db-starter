package com.yonyou.cloud.zuul.db.route;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;

import com.yonyou.cloud.zuul.db.entity.RouteVersionEntity;
import com.yonyou.cloud.zuul.db.store.ZuulRouteStore;
/**
 * 
 * @author joy
 */
public class StoreProxyRouteLocator extends DiscoveryClientRouteLocator {
	private Logger logger=Logger.getLogger(StoreProxyRouteLocator.class);

    private final ZuulRouteStore store;
    private ZuulProperties properties;
    private Map<String, ZuulProperties.ZuulRoute> oldRoutes=new HashMap();

    public StoreProxyRouteLocator(String servletPath, DiscoveryClient discovery,
			ZuulProperties properties,
                                  ZuulRouteStore store) {
        super(servletPath, discovery, properties);
        this.store = store;
        this.properties=properties;
        for(Entry<String,ZuulProperties.ZuulRoute> set:properties.getRoutes().entrySet()){
        	oldRoutes.put(set.getKey(), set.getValue());
        	logger.info("inited routeKey="+set.getKey()+",value="+set.getValue());
        }
        addConfiguredRoutes(properties.getRoutes());
    }
    
    public void forceRefresh() {
    	logger.info("--StoreProxyRouteLocator.forceRefresh");
    	properties.getRoutes().clear();
        for(Entry<String,ZuulProperties.ZuulRoute> set:oldRoutes.entrySet()){
        	properties.getRoutes().put(set.getKey(), set.getValue());
        	logger.info("add inited routeKey="+set.getKey()+",value="+set.getValue());
        }
        addConfiguredRoutes(properties.getRoutes());
    }
    
    @Override
    protected LinkedHashMap<String, ZuulProperties.ZuulRoute> locateRoutes() {
    	logger.info("--StoreProxyRouteLocator.locateRoutes");

        final LinkedHashMap<String, ZuulProperties.ZuulRoute> routesMap = new LinkedHashMap<String, ZuulProperties.ZuulRoute>();
        routesMap.putAll((Map<String, ZuulProperties.ZuulRoute>)super.locateRoutes());
        return routesMap;
    }

    @Override
    protected void addConfiguredRoutes(final Map<String, ZuulProperties.ZuulRoute> routes) {
    	logger.info("--StoreProxyRouteLocator.addConfiguredRoutes");
    	List<RouteVersionEntity> versions=store.findVersionAll();
        for (ZuulProperties.ZuulRoute route : store.findAll()) {
        	routes.put(route.getPath(), route);
        	versions.forEach(item->{
        	    if(route.getServiceId()!=null &&route.getServiceId().equals(item.getServiceId())){
        	    	ZuulProperties.ZuulRoute n=new ZuulProperties.ZuulRoute();
        	    	n.setId(route.getId());
        	    	n.setLocation(n.getLocation());
        	    	n.setPath("/"+item.getVersion()+route.getPath());
        	    	n.setRetryable(route.getRetryable());
        	    	n.setServiceId(route.getServiceId()+"-"+item.getVersion());
        	    	n.setStripPrefix(route.isStripPrefix());
        	    	n.setUrl(route.getUrl());
                	routes.put(n.getPath(), n);
                	logger.info("--dynVersion,path="+n.getPath()+",serviceid="+n.getServiceId());
        	    }
        	});
        }
    }
}
