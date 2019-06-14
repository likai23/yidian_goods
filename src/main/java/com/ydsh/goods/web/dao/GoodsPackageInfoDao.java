/**
 * @filename:GoodsPackageInfoDao 2019-06-12 10:08:38
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2020 戴艺辉 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.ydsh.goods.web.entity.GoodsPackageInfo;

/**   
 * <p>自定义mapper写在这里</p>
 * 
 * <p>说明： 套餐详情表:对应的sku数据访问层</p>
 * @version: V1.0
 * @author: 戴艺辉
 * 
 */
@Mapper
public interface GoodsPackageInfoDao extends BaseMapper<GoodsPackageInfo> {
	
}
