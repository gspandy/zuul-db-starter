package com.yonyou.cloud.zuul.db.store;

import java.util.List;

import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;

import com.yonyou.cloud.zuul.db.entity.RouteVersionEntity;

/**
 * 
 * @author joy
 */
public interface ZuulRouteStore {
    /**
     * 读取所有的路由信息
     * @return zuul的路由集合
     */
    List<ZuulProperties.ZuulRoute> findAll();
    /**
     * 读取所有的路由版本信息
     * @return zuul的路由版本集合
     */
    List<RouteVersionEntity> findVersionAll();
}
