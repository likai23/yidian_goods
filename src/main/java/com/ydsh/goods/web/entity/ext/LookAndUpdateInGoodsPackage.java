/**
 * @filename:GoodsPackage 2019-06-14 09:24:41
 * @project ydsh-saas-service-goods  V1.0
 * Copyright(c) 2020 戴艺辉 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.entity.ext;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 代码自动生成，请勿修改
 * </p>
 * 
 * <p>
 * 说明： 套餐商品管理实体类
 * </P>
 * 
 * @version: V1.0
 * @author: 戴艺辉
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LookAndUpdateInGoodsPackage implements Serializable {

	private static final long serialVersionUID = 1560475481193L;
	@JSONField(serialize = false)
    @TableField(exist = false)
	@ApiModelProperty(name = "id", value = "主键ID")
	private Long id;
	@JSONField(serialize = false)
    @TableField(exist = false)
	@ApiModelProperty(name = "lookSign", value = "查看套餐商品值为lookPackageGoods，修改进入查看商品值为lookPackageGoodsWtihStatus")
	private String lookSign;
	@ApiModelProperty(name = "reviewStatus", value = "审批状态")
	private String reviewStatus;
	@ApiModelProperty(name = "reviewRemarks", value = "审批备注")
	private String reviewRemarks;
}
