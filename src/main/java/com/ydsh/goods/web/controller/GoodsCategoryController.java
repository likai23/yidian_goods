/**
 * @filename:GoodsCategoryController 2019-06-12 10:08:38
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2020 姚仲杰 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.controller;

import com.ydsh.goods.web.controller.base.AbstractController;
import com.ydsh.goods.web.entity.GoodsCategory;
import com.ydsh.goods.web.service.GoodsCategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
/**   
 * <p>自定义方法写在这里</p>
 * 
 * <p>说明： 商品类目管理API接口层</P>
 * @version: V1.0
 * @author: 姚仲杰
 *
 */
@Api(description = "商品类目管理",value="商品类目管理" )
@RestController
@RequestMapping("/goodsCategory")
@Slf4j
public class GoodsCategoryController extends AbstractController<GoodsCategoryService,GoodsCategory>{
	
}