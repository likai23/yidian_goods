/**
 * @filename:GoodsPackageController 2019-06-12 10:08:38
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2020 姚仲杰 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.controller;

import com.ydsh.goods.web.controller.base.AbstractController;
import com.ydsh.goods.web.entity.GoodsPackage;
import com.ydsh.goods.web.service.GoodsPackageService;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
/**   
 * <p>自定义方法写在这里</p>
 * 
 * <p>说明： 套餐商品管理API接口层</P>
 * @version: V1.0
 * @author: 姚仲杰
 *
 */
@Api(description = "套餐商品管理",value="套餐商品管理" )
@RestController
@RequestMapping("/goodsPackage")
@Slf4j
public class GoodsPackageController extends AbstractController<GoodsPackageService,GoodsPackage>{
	private static Timestamp now = new Timestamp(System.currentTimeMillis());

	private static Logger logger = LoggerFactory.getLogger(GoodsCardController.class);

	// 0常量值
	private static final int VALUE_0 = 0;
	// 1常量值
	private static final int VALUE_1 = 1;
	// 2常量值
	private static final int VALUE_2 = 2;
	// 3常量值
	private static final int VALUE_3 = 3;
	// 4常量值
	private static final int VALUE_4 = 4;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}