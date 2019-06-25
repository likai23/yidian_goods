/**
 * @filename:GoodsCard 2019-06-14 09:24:40
 * @project ydsh-saas-service-goods  V1.0
 * Copyright(c) 2020 戴艺辉 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;

/**
 * <p>
 * 代码自动生成，请勿修改
 * </p>
 * 
 * <p>
 * 说明： 卡券商品表实体类
 * </P>
 * 
 * @version: V1.0
 * @author: 戴艺辉
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsCardAndSkuDto implements Serializable {

	private static final long serialVersionUID = 1560475480915L;
	// card字段
	@ApiModelProperty(name = "gcId", value = "商品ID")
	private Long gcId;
	@ApiModelProperty(name = "gcNo", value = "商品编码")
	private String gcNo;
	@ApiModelProperty(name = "goodName", value = "商品名称")
	private String goodName;
	@ApiModelProperty(name = "goodForshort", value = "商品简称")
	private String goodForshort;
	@ApiModelProperty(name = "goodAttribute", value = "商品属性：1.卡券，2.空卡档，3.实体卡")
	private String goodAttribute;
	@ApiModelProperty(name = "goodType", value = "商品类型：1.代金券，2.优惠券，3.兌换卷，4.免费劵，5.电子预付卡")
	private String goodType;
	@ApiModelProperty(name = "goodProperty", value = "商品性质：1.自建，2.采购，3.实时抓码")
	private String goodProperty;
	@ApiModelProperty(name = "goodShape", value = "卡券形式：1.卡号卡密，2.卡密，3.短链，4短链验证码")
	private String goodShape;
	@ApiModelProperty(name = "goodCategoryId", value = "商品类目表id")
	private String goodCategoryId;
	@ApiModelProperty(name = "goodStatus", value = "卡卷状态：1.上架，2.下架，3.作废")
	private String goodStatus;
	@ApiModelProperty(name = "reviewId", value = "审核人id")
	private String reviewId;
	@ApiModelProperty(name = "reviewStatus", value = "审批状态：0-待审核，1-审核通过，2-审核不通过")
	private String reviewStatus;
	@ApiModelProperty(name = "reviewRemarks", value = "审批备注")
	private String reviewRemarks;
	@ApiModelProperty(name = "createId", value = "创建人ID")
	private Long createId;
	@ApiModelProperty(name = "updateId", value = "修改人ID")
	private Long updateId;
	@ApiModelProperty(name = "status", value = "数据状态（1：正常[√]；0：删除）")
	private Integer status;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@ApiModelProperty(name = "createTime", value = "创建时间（自动生成, MySQL 5.7+）")
	private Date createTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@ApiModelProperty(name = "updateTime", value = "修改时间（自动生成, MySQL 5.7+）")
	private Date updateTime;
	// sku字段
	@ApiModelProperty(name = "gcsId", value = "sku ID")
	private Long gcsId;
	@ApiModelProperty(name = "gcsNo", value = "sku编码")
	private String gcsNo;
	@ApiModelProperty(name = "skuName", value = "sku名称")
	private String skuName;
	@ApiModelProperty(name = "gaaId", value = "商品销售属性管理新增属性id(sku销售属性,如面值，时效)")
	private String gaaId;
	@ApiModelProperty(name = "sdId", value = "供应商id，多存")
	private String sdId;
	@ApiModelProperty(name = "defaultAmount", value = "默认指导价")
	private String defaultAmount;
	@ApiModelProperty(name = "noticketAmount", value = "不带票指导价")
	private String noticketAmount;
	@ApiModelProperty(name = "ticketSomeamount", value = "带票同行价")
	private String ticketSomeamount;
	@ApiModelProperty(name = "noticketSomeamount", value = "不带票同行价")
	private String noticketSomeamount;
	@ApiModelProperty(name = "ticketAmount", value = "带票指导价")
	private String ticketAmount;
	@JSONField(serialize = false)
	@TableField(exist = false)
	@ApiModelProperty(name = "skuCardList", value = "sku的list列表包含skuName='sku名称'，gaaId='销售属性'，sdId='供应商id'，skuCardPlatformInfoList='sku平台信息包含{pmId='平台id',skuCoverPhoto='商品封面图',skuMainPhoto='商品主图',skuDesc='商品详情'}'  ")                                                                       
	private List<Map<String, Object>> skuCardList;
}
