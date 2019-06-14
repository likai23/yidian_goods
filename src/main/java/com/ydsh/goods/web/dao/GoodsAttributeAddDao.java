/**
 * @filename:GoodsAttributeAddDao 2019-06-12 10:08:37
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2020 姚仲杰 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydsh.goods.web.entity.GoodsAttributeAdd;

/**
 * <p>
 * 自定义mapper写在这里
 * </p>
 * 
 * <p>
 * 说明： 商品销售属性管理新增属性数据访问层
 * </p>
 * 
 * @version: V1.0
 * @author: 姚仲杰
 * 
 */
@Mapper
public interface GoodsAttributeAddDao extends BaseMapper<GoodsAttributeAdd> {

	
	@Select("SELECT " + " gam.id AS gamId," + "gam.attribute_name AS attributeName,"
			+ "gam.`gc_id` AS gcId," + "gam.`create_time` AS gamcreateTime," + "gam.`status` AS gamStatus,"
			+ "gaa.`id` AS gaaId," + "gaa.`attribute_value` AS attributeValue,"
			+ "gaa.`attribute_order` AS attributeOrder," + "gaa.`create_time` AS gaacreateTime  " + "FROM "
			+ "goods_attribute_manager gam," + "goods_attribute_add gaa" + " WHERE "
			+ " gam.`id`=gaa.`gam_id`")
	Page<Map<String, Object>> selectAttributeAddWithManager(IPage<Map<String, Object>> page,
			@Param(Constants.WRAPPER) Wrapper<Map<String, Object>> queryWrapper);
}
