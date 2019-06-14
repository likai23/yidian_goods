/**
 * @filename:GoodsAttributeAddController 2019-06-12 10:08:37
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2020 戴艺辉 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydsh.generator.common.JsonResult;
import com.ydsh.generator.common.PageParam;
import com.ydsh.goods.common.enums.DBDictionaryEnumManager;
import com.ydsh.goods.common.enums.ErrorCode;
import com.ydsh.goods.common.exception.SystemException;
import com.ydsh.goods.common.util.TextUtils;
import com.ydsh.goods.web.controller.base.AbstractController;
import com.ydsh.goods.web.entity.GoodsAttributeAdd;
import com.ydsh.goods.web.service.GoodsAttributeAddService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 自定义方法写在这里
 * </p>
 * 
 * <p>
 * 说明： 商品销售属性管理新增属性API接口层
 * </P>
 * 
 * @version: V1.0
 * @author: 戴艺辉
 *
 */
@Api(description = "商品销售属性管理新增属性", value = "商品销售属性管理新增属性")
@RestController
@RequestMapping("/goodsAttributeAdd")
@Slf4j
public class GoodsAttributeAddController extends AbstractController<GoodsAttributeAddService, GoodsAttributeAdd> {

	private static Logger logger = LoggerFactory.getLogger(GoodsAttributeAddController.class);

	@Autowired
	private GoodsAttributeAddService goodsAttributeAddService;
	
	
	
	/**
	 * @explain 分页条件查询用户   
	 * @param   pageParam
	 * @return  JsonResult
	 * @author  戴艺辉
	 * @time    2019-06-12 10:08:37
	 */
	@RequestMapping(value = "/getAttributePages", method = RequestMethod.GET)
	@ApiOperation(value = "分页查询销售属性主表和副表", notes = "分页查询返回[IPage<T>],作者：")
	public JsonResult<IPage<Map<String, Object>>> getAttributeAddWtithManagerPages(PageParam<Map<String, Object>> pageParam) {
		if(pageParam.getPageSize()>500) {
			logger.error("分页最大限制500，" +pageParam);
			result.error("分页最大限制500");
		}
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageParam.getPageNum(), pageParam.getPageSize());
		JsonResult<IPage<Map<String, Object>>> returnPage = new JsonResult<IPage<Map<String, Object>>>();
		Map<String, Object> queryWrapper = new HashMap<String, Object>();
		// 分页数据
 		IPage<Map<String, Object>> pageData = goodsAttributeAddService.selectAttributeAddWithManager(page, queryWrapper);
		result.success("添加成功");
		returnPage.success(pageData);

		return returnPage;
	}

	
	
	/**
	 * 
	 * 新增商品销售副属性
	 * 
	 * @param @param  param
	 * @param @return
	 * @param @throws CoreException
	 * @return
	 */
	@RequestMapping(value = "/saveAttributeSlave", method = RequestMethod.POST)
	@ApiOperation(value = "新增商品销售副属性", notes = "作者：")
	public JsonResult<Object> saveAttributeSlave(@RequestBody GoodsAttributeAdd param) {
		JsonResult<Object> result = new JsonResult<Object>();
		if (param.getGamId() == null || param.getAttributeValue() == null || param.getAttributeOrder() == null
				|| param.getStatus() == null) {
			logger.error("请求参数为空，" + param);
			throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "请求参数为空", new Exception());
		}
		baseService.save(param);
		result.success("保存成功");
		return result;
	}

	/**
	 * 
	 * 1-修改商品销售副属性基本信息 2-修改商品销售副属性状态
	 * 
	 * @param @param  param
	 * @param @return
	 * @param @throws CoreException
	 * @return
	 */
	@RequestMapping(value = "/updateAttributeSlave", method = RequestMethod.POST)
	@ApiOperation(value = "修改商品销售副属性", notes = "作者：")
	public JsonResult<Object> updateAttributeSlave(@RequestBody Map<String, Object> param) {
		JsonResult<Object> result = new JsonResult<Object>();
		String updateSign = TextUtils.getMapForKeyToString(param, "updateSign");
		if (TextUtils.isEmpty(updateSign)) {
			logger.error("请求参数为空，" + param);
			throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "请求参数为空", new Exception());
		}
		// 修改商品销售副属性基本信息
		if (updateSign.equals("updateAttributeSlave")) {
			String id = TextUtils.getMapForKeyToString(param, "id");
			String attributeValue = TextUtils.getMapForKeyToString(param, "attributeValue");
			String attributeOrder = TextUtils.getMapForKeyToString(param, "attributeOrder");
			String status = TextUtils.getMapForKeyToString(param, "status");
			if (TextUtils.isEmptys(id, attributeValue, attributeOrder, status)) {
				logger.error("请求参数为空，" + param);
				throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "请求参数为空", new Exception());
			}
			GoodsAttributeAdd goodsAttributeAdd = new GoodsAttributeAdd();
			goodsAttributeAdd.setId(Long.parseLong(id));
			goodsAttributeAdd.setAttributeValue(attributeValue);
			goodsAttributeAdd.setAttributeOrder(Integer.parseInt(attributeOrder));
			goodsAttributeAdd.setStatus(Integer.parseInt(status));
			baseService.updateById(goodsAttributeAdd);
			result.success("修改成功！");
		}
		// 修改商品销售副属性状态
		else if (updateSign.equals("updateAttributeSlaveWithStatus")) {
			String id = TextUtils.getMapForKeyToString(param, "id");
			String status = TextUtils.getMapForKeyToString(param, "status");
			if (TextUtils.isEmptys(id, status)) {
				logger.error("请求参数为空，" + param);
				throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "请求参数为空", new Exception());
			}
			GoodsAttributeAdd goodsAttributeAddCheck = baseService.getById(id);
			if (goodsAttributeAddCheck == null) {
				logger.error("请求参数异常，");
				throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "请求参数异常", new Exception());
			}
			//启用
			if (status.equals(DBDictionaryEnumManager.user_status_0.getkey())) {
				if (goodsAttributeAddCheck.getStatus() == Integer
						.parseInt(DBDictionaryEnumManager.user_status_1.getkey())) {
					GoodsAttributeAdd goodsAttributeAdd = new GoodsAttributeAdd();
					goodsAttributeAdd.setId(Long.parseLong(id));
					goodsAttributeAdd.setStatus(Integer.parseInt(status));
					baseService.updateById(goodsAttributeAdd);
					result.success("修改成功！");
				}else {
					logger.error("不是禁用状态，不可修改为启用！");
					result.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
					result.error("不是禁用状态，不可修改为启用！");
				}
			}
			//禁用
			else if (status.equals(DBDictionaryEnumManager.user_status_1.getkey())) {
				if (goodsAttributeAddCheck.getStatus() == Integer
						.parseInt(DBDictionaryEnumManager.user_status_0.getkey())) {
					GoodsAttributeAdd goodsAttributeAdd = new GoodsAttributeAdd();
					goodsAttributeAdd.setId(Long.parseLong(id));
					goodsAttributeAdd.setStatus(Integer.parseInt(status));
					baseService.updateById(goodsAttributeAdd);
					result.success("修改成功！");
				}else {
					logger.error("不是启用状态，不可修改为禁用！");
					result.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
					result.error("不是启用状态，不可修改为禁用！");
				}
			}

		} else {
			logger.error("请求参数异常，");
			throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "请求参数异常", new Exception());
		}
		return result;
	}

}