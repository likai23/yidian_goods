/**
 * @filename:GoodsPublicPrice 2019-06-14 09:24:41
 * @project ydsh-saas-service-goods  V1.0
 * Copyright(c) 2020 戴艺辉 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ydsh.goods.web.entity.GoodsPackage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;

/**   
 * <p>代码自动生成，请勿修改</p>
 * 
 * <p>说明： 商品公共价管理表实体类</P>
 * @version: V1.0
 * @author: 戴艺辉
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LookRetrunGoodsPublicPriceDto implements Serializable {

	private static final long serialVersionUID = 1560475481385L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "主键ID")
	private Long id;
	@ApiModelProperty(name = "gppNo" , value = "价格调整单号")
	private String gppNo;
	@ApiModelProperty(name = "priceStatus" , value = "调整单状态")
	private String priceStatus;
	@ApiModelProperty(name = "goodsType" , value = "商品类型")
	private String goodsType;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "submitTime" , value = "提交时间")
	private Date submitTime;
	@ApiModelProperty(name = "submitId" , value = "提交人id")
	private String submitId;
	@ApiModelProperty(name = "remarks" , value = "提交备注")
	private String remarks;
	@ApiModelProperty(name = "reviewId" , value = "审核人id")
	private String reviewId;
	@ApiModelProperty(name = "reviewStatus" , value = "审核状态：0-待审核，1-审核通过，2-审核不通过")
	private String reviewStatus;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "reviewTime" , value = "审核时间")
	private Date reviewTime;
	@ApiModelProperty(name = "reviewRemarks" , value = "审核意见")
	private String reviewRemarks;
	@ApiModelProperty(name = "updateId" , value = "修改人ID")
	private Long updateId;
	@ApiModelProperty(name = "status" , value = "数据状态（1：正常[√]；0：删除）")
	private Integer status;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "updateTime" , value = "修改时间（自动生成, MySQL 5.7+）")
	private Date updateTime;
	@ApiModelProperty(name = "skuList" , value = "卡券sku关联信息,商品类型为0时（goodsType）")
	private List<GoodsCardAndSkuDto> skuList;
	@ApiModelProperty(name = "packageList" , value = "套餐关联信息,商品类型为1时（goodsType）")
	private List<GoodsPackage> packageList;
}
