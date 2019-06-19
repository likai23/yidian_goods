/**
 * @filename:GoodsPublicPrice 2019-06-14 09:24:41
 * @project ydsh-saas-service-goods  V1.0
 * Copyright(c) 2020 戴艺辉 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.entity.dto;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;

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
 * 说明： 商品公共价管理表实体类
 * </P>
 * 
 * @version: V1.0
 * @author: 戴艺辉
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LookAndUpdateTakeInGoodsPublicPriceDto implements Serializable {

	private static final long serialVersionUID = 1560475481385L;
	@JSONField(serialize = false)
	@TableField(exist = false)
	@ApiModelProperty(name = "id", value = "主键ID")
	private Long id;
	@JSONField(serialize = false)
    @TableField(exist = false)
	@ApiModelProperty(name = "lookSign", value = "修改进入为updateTakeIn,查看进入为lookSign")
	private String lookSign;
}
