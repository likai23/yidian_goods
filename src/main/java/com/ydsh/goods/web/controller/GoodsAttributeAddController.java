/**
 * @filename:GoodsAttributeAddController 2019-06-12 10:08:37
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2020 戴艺辉 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.controller;

import java.util.HashMap;
import java.util.Map;

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
import com.ydsh.goods.common.exception.BizException;
import com.ydsh.goods.common.util.TextUtils;
import com.ydsh.goods.web.controller.base.AbstractController;
import com.ydsh.goods.web.entity.GoodsAttributeAdd;
import com.ydsh.goods.web.entity.dto.GoodsAttributeAddAndManager;
import com.ydsh.goods.web.entity.dto.GoodsAttributeAddDto;
import com.ydsh.goods.web.entity.dto.updateGoodsAttributeManagerStatus;
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


	@Autowired
	private GoodsAttributeAddService goodsAttributeAddService;

	/**
	 * @explain 分页条件查询用户
	 * @param pageParam
	 * @return JsonResult
	 * @author 戴艺辉
	 * @time 2019-06-12 10:08:37
	 */
	@RequestMapping(value = "/getAttributePages", method = RequestMethod.GET)
	@ApiOperation(value = "分页查询销售属性主表和副表", notes = "分页查询返回[IPage<T>],作者：")
	public JsonResult<IPage<GoodsAttributeAddAndManager>> getAttributeAddWtithManagerPages(
			PageParam<GoodsAttributeAddDto> pageParam) {
		if (pageParam.getPageSize() > 500) {
			log.error("分页最大限制500，" + pageParam);
			result.error("分页最大限制500");
		}
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageParam.getPageNum(), pageParam.getPageSize());
		JsonResult<IPage<GoodsAttributeAddAndManager>> returnPage = new JsonResult<IPage<GoodsAttributeAddAndManager>>();
		Map<String, Object> queryWrapper = new HashMap<String, Object>();
		// 分页数据
		IPage<GoodsAttributeAddAndManager> pageData = goodsAttributeAddService.selectAttributeAddWithManager(page,
				queryWrapper);
		result.success("添加成功");
		returnPage.success(pageData);

		return returnPage;
	}

	/**
	 * 
	 * *新增商品销售副属性
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
			log.error("请求参数为空，" + param);
			throw new BizException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "请求参数为空");
		}
		baseService.save(param);
		result.success("保存成功");
		return result;
	}

	/**
	 * 
	* * 修改商品销售副属性基本信息
	*
	* @param @param param
	* @param @return
	* @return
	 */
	@RequestMapping(value = "/updateAttributeSlave", method = RequestMethod.POST)
	@ApiOperation(value = "修改商品销售副属性基本信息", notes = "作者：")
	public JsonResult<Object> updateAttributeSlave(@RequestBody GoodsAttributeAddDto param) {
		JsonResult<Object> result = new JsonResult<Object>();
		String id = String.valueOf(param.getId());
		String attributeValue = param.getAttributeValue();
		String attributeOrder = String.valueOf(param.getAttributeOrder());
		String status = String.valueOf(param.getStatus());
		if (TextUtils.isEmptys(id, attributeValue, attributeOrder, status)) {
			log.error("请求参数为空，" + param);
			throw new BizException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "请求参数为空");
		}
		GoodsAttributeAdd goodsAttributeAdd = new GoodsAttributeAdd();
		goodsAttributeAdd.setId(Long.parseLong(id));
		goodsAttributeAdd.setAttributeValue(attributeValue);
		goodsAttributeAdd.setAttributeOrder(Integer.parseInt(attributeOrder));
		goodsAttributeAdd.setStatus(Integer.parseInt(status));
		baseService.updateById(goodsAttributeAdd);
		result.success("修改成功！");
		return result;
	}
	/**
	 * 
	 *  *修改商品销售副属性状态
	 * 
	 * @param @param  param
	 * @param @return
	 * @param @throws CoreException
	 * @return
	 */
	@RequestMapping(value = "/updateAttributeSlaveWithStatus", method = RequestMethod.POST)
	@ApiOperation(value = "修改商品销售副属性状态", notes = "作者：")
	public JsonResult<Object> updateAttributeSlaveWithStatus(@RequestBody updateGoodsAttributeManagerStatus param) {
		JsonResult<Object> result = new JsonResult<Object>();
		// 修改商品销售副属性状态
			String id = String.valueOf(param.getId());
			String status = String.valueOf(param.getStatus());
			if (TextUtils.isEmptys(id, status)) {
				log.error("请求参数为空，" + param);
				throw new BizException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "请求参数为空");
			}
			GoodsAttributeAdd goodsAttributeAddCheck = baseService.getById(id);
			if (goodsAttributeAddCheck == null) {
				log.error("请求参数异常，");
				throw new BizException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "请求参数异常");
			}
			// 启用
			if (status.equals(DBDictionaryEnumManager.user_status_0.getkey())) {
				if (goodsAttributeAddCheck.getStatus() == Integer
						.parseInt(DBDictionaryEnumManager.user_status_1.getkey())) {
					GoodsAttributeAdd goodsAttributeAdd = new GoodsAttributeAdd();
					goodsAttributeAdd.setId(Long.parseLong(id));
					goodsAttributeAdd.setStatus(Integer.parseInt(status));
					baseService.updateById(goodsAttributeAdd);
					result.success("修改成功！");
				} else {
					log.error("不是禁用状态，不可修改为启用！");
					result.error("不是禁用状态，不可修改为启用！");
					return result;
				}
			}
			// 禁用
			else if (status.equals(DBDictionaryEnumManager.user_status_1.getkey())) {
				if (goodsAttributeAddCheck.getStatus() == Integer
						.parseInt(DBDictionaryEnumManager.user_status_0.getkey())) {
					GoodsAttributeAdd goodsAttributeAdd = new GoodsAttributeAdd();
					goodsAttributeAdd.setId(Long.parseLong(id));
					goodsAttributeAdd.setStatus(Integer.parseInt(status));
					baseService.updateById(goodsAttributeAdd);
					result.success("修改成功！");
				} else {
					log.error("不是启用状态，不可修改为禁用！");
					result.error("不是启用状态，不可修改为禁用！");
					return result;
				}
			}
		
		return result;
	}

}