/**
 * @filename:GoodsPackageDao 2019-06-12 10:08:38
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2020 戴艺辉 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ydsh.goods.web.entity.GoodsPackage;
import com.ydsh.goods.web.entity.dto.GoodsPackageAndSkuDto;

/**   
 * <p>自定义mapper写在这里</p>
 * 
 * <p>说明： 套餐商品管理数据访问层</p>
 * @version: V1.0
 * @author: 戴艺辉
 * 
 */
@Mapper
public interface GoodsPackageDao extends BaseMapper<GoodsPackage> {
	
	/**
	 * 
	* 套餐商品和关联sku分页查询
	*
	* @param @param pageParam
	* @param @return
	* @return
	 */
    IPage<GoodsPackageAndSkuDto> getPackageAndSkuPages(IPage<Map<String,Object>> page, @Param("queryWrapper") GoodsPackageAndSkuDto queryWrapper);
}
