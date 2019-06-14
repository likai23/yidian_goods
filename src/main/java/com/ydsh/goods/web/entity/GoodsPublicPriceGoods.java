/**
 * @filename:GoodsPublicPriceGoods 2019-06-14 09:24:41
 * @project ydsh-saas-service-goods  V1.0
 * Copyright(c) 2020 戴艺辉 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;

/**   
 * <p>代码自动生成，请勿修改</p>
 * 
 * <p>说明： 商品公共价管理对应商品表实体类</P>
 * @version: V1.0
 * @author: 戴艺辉
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsPublicPriceGoods implements Serializable {

	private static final long serialVersionUID = 1560475481441L;
	
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(name = "id" , value = "主键ID")
	private Long id;
	@ApiModelProperty(name = "gppId" , value = "商品公共价管理表的id")
	private Long gppId;
	@ApiModelProperty(name = "goodsId" , value = "sku表id或套餐id")
	private Long goodsId;
	@ApiModelProperty(name = "defaultAmount" , value = "默认指导价")
	private Long defaultAmount;
	@ApiModelProperty(name = "noticketAmount" , value = "不带票指导价")
	private Long noticketAmount;
	@ApiModelProperty(name = "ticketSomeamount" , value = "带票同行价")
	private Long ticketSomeamount;
	@ApiModelProperty(name = "noticketSomeamount" , value = "不带票同行价")
	private Long noticketSomeamount;
	@ApiModelProperty(name = "ticketAmount" , value = "带票指导价")
	private Long ticketAmount;
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
	@ApiModelProperty(name = "createTime" , value = "创建时间（自动生成, MySQL 5.6+）")
	private Date createTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "updateTime" , value = "修改时间（自动生成, MySQL 5.7+）")
	private Date updateTime;
}
