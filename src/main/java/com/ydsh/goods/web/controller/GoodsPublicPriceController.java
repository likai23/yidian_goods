/**
 * @filename:GoodsPublicPriceController 2019-06-12 10:08:38
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2020 戴艺辉 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.controller;

import com.ydsh.goods.web.controller.base.AbstractController;
import com.ydsh.goods.web.entity.GoodsPublicPrice;
import com.ydsh.goods.web.service.GoodsPublicPriceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
/**   
 * <p>自定义方法写在这里</p>
 * 
 * <p>说明： 商品公共价管理表API接口层</P>
 * @version: V1.0
 * @author: 戴艺辉
 *
 */
@Api(description = "商品公共价管理表",value="商品公共价管理表" )
@RestController
@RequestMapping("/goodsPublicPrice")
@Slf4j
public class GoodsPublicPriceController extends AbstractController<GoodsPublicPriceService,GoodsPublicPrice>{
	
}