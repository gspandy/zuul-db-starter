package com.yonyou.cloud.zuul.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.cloud.zuul.db.entity.RouteEntity;
import com.yonyou.cloud.zuul.db.entity.RouteVersionEntity;

import tk.mybatis.mapper.common.Mapper;
 
/**
 * 
 * @author joy
 */

@Transactional(rollbackFor={Exception.class})
public interface RouteMapper extends Mapper<RouteEntity>{
    /**
     * 读取所有的路由信息
     * @return 路由集合
     */
    @Select(" SELECT id,path,service_id as serviceId,url,strip_prefix as stripPrefix,retryable from gate_routes order by path desc" )
    @ResultType(RouteEntity.class)
    public List<RouteEntity> findAll(); 
    /**
     * 读取所有的路由版本信息
     * @return 路由集合
     */
    @Select(" SELECT id,service_id as ServiceId,version from gate_routes_version order by ServiceId desc" )
    @ResultType(RouteVersionEntity.class)
    public List<RouteVersionEntity> findVersionAll(); 
     
}
 


 
