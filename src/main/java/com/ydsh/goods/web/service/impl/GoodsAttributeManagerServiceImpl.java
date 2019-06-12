/**
 * @filename:GoodsAttributeManagerServiceImpl 2019-06-12 10:08:37
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2018 姚仲杰 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.service.impl;

import com.ydsh.goods.web.entity.GoodsAttributeManager;
import com.ydsh.goods.web.dao.GoodsAttributeManagerDao;
import com.ydsh.goods.web.service.GoodsAttributeManagerService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**   
 * <p>自定义serviceImpl写在这里</p>
 * 
 * <p>说明： 商品销售属性管理服务实现层</P>
 * @version: V1.0
 * @author: 姚仲杰
 * 
 */
@Service
public class GoodsAttributeManagerServiceImpl  extends ServiceImpl<GoodsAttributeManagerDao, GoodsAttributeManager> implements GoodsAttributeManagerService  {
	
}