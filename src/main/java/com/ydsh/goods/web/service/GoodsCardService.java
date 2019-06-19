/**
 * @filename:GoodsCardService 2019-06-12 10:08:37
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2020 戴艺辉 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.service;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydsh.goods.web.entity.GoodsCard;
import com.ydsh.goods.web.entity.dto.GoodsCardAndSkuDto;
/**   
 * <p>自定义service写在这里</p>
 * 
 * <p>说明： 卡券商品表服务层</P>
 * @version: V1.0
 * @author: 戴艺辉
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
	 Page<Map<String, Object>> selectCardAndSKUPage(IPage<Map<String, Object>> page, GoodsCardAndSkuDto queryWrapper);
	  /**
	   * 
	   * *连表查询 销售属性主表和副表
	   *
	   * @param @param page
	   * @param @param queryWrapper
	   * @param @return
	   * @return
	   */
	  Map<String, Object> selectCardAndSKUPage(@Param("queryWrapper") GoodsCardAndSkuDto queryWrapper);
}