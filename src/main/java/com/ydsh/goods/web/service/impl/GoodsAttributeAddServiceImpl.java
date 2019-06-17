/**
 * @filename:GoodsAttributeAddServiceImpl 2019-06-12 10:08:37
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2018 戴艺辉 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.service.impl;

import com.ydsh.goods.web.entity.GoodsAttributeAdd;
import com.ydsh.goods.web.dao.GoodsAttributeAddDao;
import com.ydsh.goods.web.service.GoodsAttributeAddService;


import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**   
 * <p>自定义serviceImpl写在这里</p>
 * 
 * <p>说明： 商品销售属性管理新增属性服务实现层</P>
 * @version: V1.0
 * @author: 戴艺辉
 * 
 */
@Service
public class GoodsAttributeAddServiceImpl  extends ServiceImpl<GoodsAttributeAddDao, GoodsAttributeAdd> implements GoodsAttributeAddService  {
	/**
	 * 
	* 连表查询 销售属性主表和副表
	*
	* @param @param page
	* @param @param queryWrapper
	* @param @return
	* @return
	 */
	 public IPage<Map<String, Object>> selectAttributeAddWithManager(IPage<Map<String, Object>> page, Map<String,Object> queryWrapper){
		 return baseMapper.selectAttributeAddWithManager(page, queryWrapper);
	 }
}