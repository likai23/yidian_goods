/**
 * @filename:GoodsCategoryService 2019-06-12 10:08:38
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2020 戴艺辉 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.service;

import com.ydsh.goods.web.entity.GoodsCategory;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
/**   
 * <p>自定义service写在这里</p>
 * 
 * <p>说明： 商品类目管理服务层</P>
 * @version: V1.0
 * @author: 戴艺辉
 * 
 */
public interface GoodsCategoryService extends IService<GoodsCategory> {
	
    /**
     * 
    ** 分页查询
    *
    * @param @param page
    * @param @param queryWrapper
    * @param @return
    * @return
     */
    public IPage<Map<String, Object>> getGoodsCategoryPages(IPage<Map<String, Object>> page, @Param("queryWrapper") Map<String, Object> queryWrapper);
}