/**
 * @filename:GoodsAttributeAdd 2019-06-14 09:24:40
 * @project ydsh-saas-service-goods  V1.0
 * Copyright(c) 2020 戴艺辉 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.entity.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**   
 * <p>代码自动生成，请勿修改</p>
 * 
 * <p>说明： 商品销售属性管理新增属性实体类</P>
 * @version: V1.0
 * @author: 戴艺辉
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GoodsAttributeAddAndManager implements Serializable {

	private static final long serialVersionUID = 1560475480492L;
	
	/**
	 * add
	 */
	@ApiModelProperty(name = "id" , value = "商品销售属性管理主表属性主键ID")
	private Long gaaId;
	@ApiModelProperty(name = "attributeValue" , value = "商品销售属性管理主表属性属性值")
	private String attributeValue;
	@ApiModelProperty(name = "attributeOrder" , value = "商品销售属性管理主表属性排序")
	private Integer attributeOrder;
	@ApiModelProperty(name = "gaaStatus" , value = "商品销售属性管理主表属性状态")
	private Integer gaaStatus;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "createTime" , value = "创建时间（自动生成, MySQL 5.7+）")
	private Date gaaCreatetime;
	/**
	 * manager
	 */
	@ApiModelProperty(name = "id" , value = "商品销售属性管理副表属性主键ID")
	private Long gamId;
	@ApiModelProperty(name = "attributeName" , value = "商品销售属性管理副表属性名称")
	private String attributeName;
	@ApiModelProperty(name = "gcId" , value = "商品销售属性管理副表商品类目管理id")
	private Long gcId;
	@ApiModelProperty(name = "gamStatus" , value = "商品销售属性管理副表状态")
	private Integer gamStatus;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "gamCreatetime" , value = "商品销售属性管理副表创建时间")
	private Date gamCreatetime;
}
