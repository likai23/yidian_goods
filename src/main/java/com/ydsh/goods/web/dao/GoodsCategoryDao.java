/**
 * @filename:GoodsCategoryDao 2019-06-12 10:08:38
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2020 戴艺辉 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ydsh.goods.web.entity.GoodsCategory;

/**   
 * <p>自定义mapper写在这里</p>
 * 
 * <p>说明： 商品类目管理数据访问层</p>
 * @version: V1.0
 * @author: 戴艺辉
 * 
 */
@Mapper
public interface GoodsCategoryDao extends BaseMapper<GoodsCategory> {
	

    /**
     * 
    ** 分页查询
    *
    * @param @param page
    * @param @param queryWrapper
    * @param @return
    * @return
     */
    IPage<Map<String, Object>> getGoodsCategoryPages(IPage<Map<String, Object>> page, @Param("queryWrapper") Map<String, Object> queryWrapper);
}
