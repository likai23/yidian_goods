/**
 * @filename:GoodsPublicPrice 2019-06-14 09:24:41
 * @project ydsh-saas-service-goods  V1.0
 * Copyright(c) 2020 戴艺辉 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.entity.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;

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
public class ReviewGoodsPublicPriceDto implements Serializable {

	private static final long serialVersionUID = 1560475481385L;
	@JSONField(serialize = false)
	@TableField(exist = false)
	@ApiModelProperty(name = "id", value = "主键ID")
	private Long id;
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
}
