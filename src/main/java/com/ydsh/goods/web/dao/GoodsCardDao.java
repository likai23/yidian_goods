/**
 * @filename:GoodsCardDao 2019-06-12 10:08:37
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2020 姚仲杰 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ydsh.goods.web.entity.GoodsCard;

/**   
 * <p>自定义mapper写在这里</p>
 * 
 * <p>说明： 卡券商品表数据访问层</p>
 * @version: V1.0
 * @author: 姚仲杰
 * 
 */
@Mapper
public interface GoodsCardDao extends BaseMapper<GoodsCard> {
	/**
	 * 
	* 连表查询 卡券商品和卡券sku
	*
	* @param @param page
	* @param @param queryWrapper
	* @param @return
	* @return
	 */
	@Select("SELECT  "
			+ "gc.id AS gcId,gc.gc_no AS gcNo,gc.good_name AS goodName,gc.good_forshort AS goodForshort,gc.good_attribute AS goodAttribute,gc.good_type AS goodType,gc.good_property AS goodProperty,"
			+ "gc.good_shape AS goodShape,gc.good_category_id AS goodCategoryId,gc.good_status AS goodStatus,gc.review_id AS reviewId,gc.review_status AS reviewStatus,gc.review_remarks AS reviewRemarks,"
			+ "gc.create_id AS createId,gc.update_id AS updateId,gc.create_time AS createTime,gc.update_time AS updateTime,"
			+ "gcs.id AS gcsId,gcs.gcs_no AS gcsNo,gcs.sku_name AS skuName,gcs.gaa_id AS gaaId,gcs.sd_id AS sdId,gcs.default_amount AS defaultAmount,gcs.noticket_amount AS noticketAmount,"
			+ "gcs.ticket_someamount AS ticketSomeamount,gcs.noticket_someamount AS noticketSomeamount,gcs.ticket_amount AS ticketAmount  "
			+ "FROM " + "goods_card gc,goods_card_sku gcs " + "WHERE " + "gc.`id`=gcs.`gc_id`")
	  IPage<Map<String, Object>> selectCardAndSKUPage(IPage<Map<String, Object>> page, @Param(Constants.WRAPPER) Wrapper<Map<String, Object>> queryWrapper);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
