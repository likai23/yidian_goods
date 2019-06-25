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
public class updateGoodsCardAndSkuStatusDto implements Serializable {

	private static final long serialVersionUID = 1560475480915L;
	// card字段
	@ApiModelProperty(name = "gcId", value = "商品ID")
	private Long gcId;
	@ApiModelProperty(name = "goodStatus", value = "商品状态")
	private String goodStatus;
}
