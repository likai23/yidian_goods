/**
 * @filename:GoodsCardServiceImpl 2019-06-12 10:08:37
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2018 戴艺辉 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.service.impl;

import com.ydsh.goods.web.entity.GoodsCard;
import com.ydsh.goods.web.entity.dto.GoodsCardAndSkuDto;
import com.ydsh.goods.web.dao.GoodsCardDao;
import com.ydsh.goods.web.service.GoodsCardService;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**   
 * <p>自定义serviceImpl写在这里</p>
 * 
 * <p>说明： 卡券商品表服务实现层</P>
 * @version: V1.0
 * @author: 戴艺辉
 * 
 */
@Service
public class GoodsCardServiceImpl  extends ServiceImpl<GoodsCardDao, GoodsCard> implements GoodsCardService  {
	
	@Autowired
	private GoodsCardDao goodsCardDao;
	/**
	 * 
	* 连表查询 卡券商品和卡券sku
	*
	* @param @param page
	* @param @param queryWrapper
	* @param @return
	* @return
	 */
	@Override
	public Page<GoodsCardAndSkuDto> selectCardAndSKUPage(IPage<Map<String, Object>> page,
			GoodsCardAndSkuDto queryWrapper) {
		// TODO Auto-generated method stub
		return goodsCardDao.selectCardAndSKUPage(page, queryWrapper);
	}
	  /**
	   * 
	   * *连表查询 销售属性主表和副表
	   *
	   * @param @param page
	   * @param @param queryWrapper
	   * @param @return
	   * @return
	   */
	  public GoodsCardAndSkuDto selectCardAndSKUPage(@Param("queryWrapper") GoodsCardAndSkuDto queryWrapper){
		  return goodsCardDao.selectCardAndSKUPage(queryWrapper);
	  }
}