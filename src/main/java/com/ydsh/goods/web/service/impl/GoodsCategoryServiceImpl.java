/**
 * @filename:GoodsCategoryServiceImpl 2019-06-12 10:08:38
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2018 戴艺辉 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.service.impl;

import com.ydsh.goods.web.entity.GoodsCategory;
import com.ydsh.goods.web.dao.GoodsCategoryDao;
import com.ydsh.goods.web.service.GoodsCategoryService;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**   
 * <p>自定义serviceImpl写在这里</p>
 * 
 * <p>说明： 商品类目管理服务实现层</P>
 * @version: V1.0
 * @author: 戴艺辉
 * 
 */
@Service
public class GoodsCategoryServiceImpl  extends ServiceImpl<GoodsCategoryDao, GoodsCategory> implements GoodsCategoryService  {
	
	@Autowired
	private GoodsCategoryDao goodsCategoryDao;
    /**
     * 
    ** 分页查询
    *
    * @param @param page
    * @param @param queryWrapper
    * @param @return
    * @return
     */
    public IPage<Map<String, Object>> getGoodsCategoryPages(IPage<Map<String, Object>> page, @Param("queryWrapper") Map<String, Object> queryWrapper){
    	return goodsCategoryDao.getGoodsCategoryPages(page, queryWrapper);
    }
}