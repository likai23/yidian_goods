/**
 * @filename:GoodsCardSku 2019-06-14 09:24:40
 * @project ydsh-saas-service-goods  V1.0
 * Copyright(c) 2020 戴艺辉 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.entity.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ydsh.goods.web.entity.GoodsCardSkuPlatforminfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**   
 * <p>代码自动生成，请勿修改</p>
 * 
 * <p>说明： 卡券商品sku表实体类</P>
 * @version: V1.0
 * @author: 戴艺辉
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GoodsCardSkuDto implements Serializable {

	private static final long serialVersionUID = 1560475480982L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "主键ID")
	private Long id;
	@ApiModelProperty(name = "gcId" , value = "卡券商品表id")
	private Long gcId;
	@ApiModelProperty(name = "gcsNo" , value = "sku编码")
	private String gcsNo;
	@ApiModelProperty(name = "skuName" , value = "sku名称")
	private String skuName;
	@ApiModelProperty(name = "gaaId" , value = "商品销售属性管理新增属性id(sku销售属性,如面值，时效)")
	private String gaaId;
	@ApiModelProperty(name = "sdId" , value = "供应商id，多存")
	private String sdId;
	@ApiModelProperty(name = "defaultAmount" , value = "默认指导价")
	private String defaultAmount;
	@ApiModelProperty(name = "noticketAmount" , value = "不带票指导价")
	private String noticketAmount;
	@ApiModelProperty(name = "ticketSomeamount" , value = "带票同行价")
	private String ticketSomeamount;
	@ApiModelProperty(name = "noticketSomeamount" , value = "不带票同行价")
	private String noticketSomeamount;
	@ApiModelProperty(name = "ticketAmount" , value = "带票指导价")
	private String ticketAmount;
	@ApiModelProperty(name = "remarks" , value = "备注")
	private String remarks;
	@ApiModelProperty(name = "createId" , value = "创建人ID")
	private Long createId;
	@ApiModelProperty(name = "updateId" , value = "修改人ID")
	private Long updateId;
	@ApiModelProperty(name = "status" , value = "数据状态（1：正常[√]；0：删除）")
	private Integer status;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "createTime" , value = "创建时间（自动生成, MySQL 5.7+）")
	private Date createTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "updateTime" , value = "修改时间（自动生成, MySQL 5.7+）")
	private Date updateTime;
	@ApiModelProperty(name = "GoodsCardSkuPlatforminfoList" , value = "sku的相关平台集合")
	private List<GoodsCardSkuPlatforminfo> GoodsCardSkuPlatforminfoList;
}
