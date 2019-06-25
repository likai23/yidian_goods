/**
 * @filename:GoodsCard 2019-06-14 09:24:40
 * @project ydsh-saas-service-goods  V1.0
 * Copyright(c) 2020 戴艺辉 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.entity.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class reviewGoodsCardAndSkuDto implements Serializable {

	private static final long serialVersionUID = 1560475480915L;
	// card字段
	@ApiModelProperty(name = "gcId", value = "商品ID")
	private Long gcId;
	@ApiModelProperty(name = "reviewId", value = "审核人id")
	private String reviewId;
	@ApiModelProperty(name = "reviewStatus", value = "审批状态：0-待审核，1-审核通过，2-审核不通过")
	private String reviewStatus;
	@ApiModelProperty(name = "reviewRemarks", value = "审批备注")
	private String reviewRemarks;
}
