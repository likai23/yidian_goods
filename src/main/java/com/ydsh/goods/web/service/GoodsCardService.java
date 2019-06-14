/**
 * @filename:GoodsCardService 2019-06-12 10:08:37
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2020 姚仲杰 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.service;

import com.ydsh.goods.web.entity.GoodsCard;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
/**   
 * <p>自定义service写在这里</p>
 * 
 * <p>说明： 卡券商品表服务层</P>
 * @version: V1.0
 * @author: 姚仲杰
 * 
 */
public interface GoodsCardService extends IService<GoodsCard> {
	/**
	 * 
	* 连表查询 卡券商品和卡券sku
	*
	* @param @param page
	* @param @param queryWrapper
	* @param @return
	* @return
	 */
	 Page<Map<String, Object>> selectCardAndSKUPage(IPage<Map<String, Object>> page, @Param(Constants.WRAPPER) Wrapper<Map<String, Object>> queryWrapper);
}