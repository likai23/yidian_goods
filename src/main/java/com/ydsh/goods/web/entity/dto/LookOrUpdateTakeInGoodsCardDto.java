/**
 * @filename:GoodsCard 2019-06-14 09:24:40
 * @project ydsh-saas-service-goods  V1.0
 * Copyright(c) 2020 戴艺辉 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;

/**   
 * <p>代码自动生成，请勿修改</p>
 * 
 * <p>说明： 卡券商品表实体类</P>
 * @version: V1.0
 * @author: 戴艺辉
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LookOrUpdateTakeInGoodsCardDto implements Serializable {

	private static final long serialVersionUID = 1560475480915L;
	
	@JSONField(serialize = false)
	@TableField(exist = false)
	@ApiModelProperty(name = "bgcId" , value = "商品id")
	private Long gcId;
	@JSONField(serialize = false)
	@TableField(exist = false)
	@ApiModelProperty(name = "gcsId" , value = "sku id")
	private Long gcsId;
	@JSONField(serialize = false)
	@TableField(exist = false)
	@ApiModelProperty(name = "getSign" , value = "修改进入查看值为lookSignWithStatus，查看进入值为lookSign")
	private String getSign;
}
