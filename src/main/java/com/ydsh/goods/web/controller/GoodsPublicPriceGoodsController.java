/**
 * @filename:GoodsPublicPriceGoodsController 2019-06-12 10:08:38
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2020 姚仲杰 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.controller;

import com.ydsh.goods.web.controller.base.AbstractController;
import com.ydsh.goods.web.entity.GoodsPublicPriceGoods;
import com.ydsh.goods.web.service.GoodsPublicPriceGoodsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
/**   
 * <p>自定义方法写在这里</p>
 * 
 * <p>说明： 商品公共价管理对应商品表API接口层</P>
 * @version: V1.0
 * @author: 姚仲杰
 *
 */
@Api(description = "商品公共价管理对应商品表",value="商品公共价管理对应商品表" )
@RestController
@RequestMapping("/goodsPublicPriceGoods")
@Slf4j
public class GoodsPublicPriceGoodsController extends AbstractController<GoodsPublicPriceGoodsService,GoodsPublicPriceGoods>{
	
}