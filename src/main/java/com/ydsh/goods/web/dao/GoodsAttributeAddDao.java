/**
 * @filename:GoodsAttributeAddDao 2019-06-12 10:08:37
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2020 姚仲杰 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.ydsh.goods.web.entity.GoodsAttributeAdd;

/**   
 * <p>自定义mapper写在这里</p>
 * 
 * <p>说明： 商品销售属性管理新增属性数据访问层</p>
 * @version: V1.0
 * @author: 姚仲杰
 * 
 */
@Mapper
public interface GoodsAttributeAddDao extends BaseMapper<GoodsAttributeAdd> {
	
}
