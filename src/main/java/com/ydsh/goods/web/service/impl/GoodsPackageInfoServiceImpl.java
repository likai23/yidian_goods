/**
 * @filename:GoodsPackageInfoServiceImpl 2019-06-12 10:08:38
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2018 姚仲杰 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.service.impl;

import com.ydsh.goods.web.entity.GoodsPackageInfo;
import com.ydsh.goods.web.dao.GoodsPackageInfoDao;
import com.ydsh.goods.web.service.GoodsPackageInfoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**   
 * <p>自定义serviceImpl写在这里</p>
 * 
 * <p>说明： 套餐详情表:对应的sku服务实现层</P>
 * @version: V1.0
 * @author: 姚仲杰
 * 
 */
@Service
public class GoodsPackageInfoServiceImpl  extends ServiceImpl<GoodsPackageInfoDao, GoodsPackageInfo> implements GoodsPackageInfoService  {
	
}