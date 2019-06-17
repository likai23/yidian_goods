/**
 * @filename:GoodsCardDao 2019-06-12 10:08:37
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2020 戴艺辉 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ydsh.goods.web.entity.GoodsCard;
import com.ydsh.goods.web.entity.ext.GoodsCardAndSku;

/**   
 * <p>自定义mapper写在这里</p>
 * 
 * <p>说明： 卡券商品表数据访问层</p>
 * @version: V1.0
 * @author: 戴艺辉
 * 
 */
@Mapper
public interface GoodsCardDao extends BaseMapper<GoodsCard> {
	/**
	 * 
	* 连表查询 销售属性主表和副表
	*
	* @param @param page
	* @param @param queryWrapper
	* @param @return
	* @return
	 */
	  Page<Map<String, Object>> selectCardAndSKUPage(IPage<Map<String, Object>> page,@Param(Constants.WRAPPER) Wrapper<GoodsCardAndSku> queryWrapper);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
