/**
 * @filename:GoodsAttributeAddService 2019-06-12 10:08:37
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2020 戴艺辉 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.service;

import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydsh.goods.web.entity.GoodsAttributeAdd;
import com.ydsh.goods.web.entity.dto.GoodsAttributeAddAndManager;
/**   
 * <p>自定义service写在这里</p>
 * 
 * <p>说明： 商品销售属性管理新增属性服务层</P>
 * @version: V1.0
 * @author: 戴艺辉
 * 
 */
public interface GoodsAttributeAddService extends IService<GoodsAttributeAdd> {
	
	/**
	 * 
	* 连表查询 销售属性主表和副表
	*
	* @param @param page
	* @param @param queryWrapper
	* @param @return
	* @return
	 */
	IPage<GoodsAttributeAddAndManager>  selectAttributeAddWithManager(IPage<Map<String, Object>> page, Map<String,Object> queryWrapper);
}