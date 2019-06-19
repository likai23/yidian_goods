/**
 * @filename:GoodsPackageService 2019-06-12 10:08:38
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2020 戴艺辉 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.service;

import com.ydsh.goods.web.entity.GoodsPackage;
import com.ydsh.goods.web.entity.dto.GoodsPackageAndSkuDto;

import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
/**   
 * <p>自定义service写在这里</p>
 * 
 * <p>说明： 套餐商品管理服务层</P>
 * @version: V1.0
 * @author: 戴艺辉
 * 
 */
public interface GoodsPackageService extends IService<GoodsPackage> {
	
	/**
	 * 
	* 套餐商品和关联sku分页查询
	*
	* @param @param pageParam
	* @param @return
	* @return
	 */
    IPage<GoodsPackageAndSkuDto> getPackageAndSkuPages(IPage<Map<String,Object>> page, GoodsPackageAndSkuDto queryWrapper);
}