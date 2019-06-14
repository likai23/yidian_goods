/**
 * @filename:GoodsAttributeManagerController 2019-06-12 10:08:37
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2020 姚仲杰 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.controller;

import com.ydsh.generator.common.JsonResult;
import com.ydsh.goods.common.enums.DBDictionaryEnumManager;
import com.ydsh.goods.common.enums.ErrorCode;
import com.ydsh.goods.common.enums.SuccessCode;
import com.ydsh.goods.common.exception.SystemException;
import com.ydsh.goods.common.util.TextUtils;
import com.ydsh.goods.web.controller.base.AbstractController;
import com.ydsh.goods.web.entity.GoodsAttributeManager;
import com.ydsh.goods.web.service.GoodsAttributeManagerService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 自定义方法写在这里
 * </p>
 * 
 * <p>
 * 说明： 商品销售属性管理API接口层
 * </P>
 * 
 * @version: V1.0
 * @author: 姚仲杰
 *
 */
@Api(description = "商品销售属性管理", value = "商品销售属性管理")
@RestController
@RequestMapping("/goodsAttributeManager")
@Slf4j
public class GoodsAttributeManagerController
		extends AbstractController<GoodsAttributeManagerService, GoodsAttributeManager> {

	private Timestamp now = new Timestamp(System.currentTimeMillis());

	private static Logger logger = LoggerFactory.getLogger(GoodsAttributeManagerController.class);

	/**
	 * 
	 * 保存商品销售属性主表
	 *
	 * @param @param  context
	 * @param @return
	 * @param @throws CoreException
	 * @return
	 */
	@RequestMapping(value = "/saveAttributeMain", method = RequestMethod.POST)
	@ApiOperation(value = "添加商品销售主属性", notes = "作者：戴艺辉")
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public JsonResult<Object> saveAttributeMain(@RequestBody GoodsAttributeManager param) {
		JsonResult<Object> result = new JsonResult<Object>();
		if (param.getGcId() == null || param.getStatus() == null || param.getAttributeName() == null) {
			logger.error("请求参数为空，");
			throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空", new Exception());
		}
		baseService.save(param);
		result.success("添加成功");
		return result;
	}

	/**
	 * 
	 * 1-修改商品销售属性主表基本信息 2-启用/禁用
	 *
	 * @param @param  context
	 * @param @return
	 * @param @throws CoreException
	 * @return
	 */
	@RequestMapping(value = "/updateAttributeMain", method = RequestMethod.POST)
	@ApiOperation(value = "添加商品销售主属性", notes = "作者：戴艺辉")
	public JsonResult<Object> updateAttributeMain(@RequestBody Map<String, Object> param) {
		JsonResult<Object> result = new JsonResult<Object>();
		String updateSign = TextUtils.getMapForKeyToString(param, "updateSign");
		//修改商品主表基本信息
		if (updateSign.equals("updateAttributeMain")) {
			String id = TextUtils.getMapForKeyToString(param, "id");
			String gcId = TextUtils.getMapForKeyToString(param, "gcId");
			String status = TextUtils.getMapForKeyToString(param, "status");
			String attributeName = TextUtils.getMapForKeyToString(param, "attributeName");
			if (TextUtils.isEmptys(id, gcId, status, attributeName)) {
				logger.error("请求参数为空，");
				throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空", new Exception());
			}
			GoodsAttributeManager goodsAttributeManagerCheck=baseService.getById(id);
			if(goodsAttributeManagerCheck==null) {
				logger.error("请求参数异常，");
				throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能异常", new Exception());
			}
			GoodsAttributeManager goodsAttributeManager = new GoodsAttributeManager();
			goodsAttributeManager.setId(Long.parseLong(id));
			goodsAttributeManager.setGcId(Long.parseLong(gcId));
			goodsAttributeManager.setStatus(Integer.parseInt(status));
			goodsAttributeManager.setAttributeName(attributeName);
			goodsAttributeManager.setUpdateTime(now);
			baseService.saveOrUpdate(goodsAttributeManager);
			result.success("修改成功");
		}
		else if(updateSign.equals("updateAttributeMainWithStatus")) {
			String id = TextUtils.getMapForKeyToString(param, "id");
			String status = TextUtils.getMapForKeyToString(param, "status");
			if (TextUtils.isEmptys(id, status)) {
				logger.error("请求参数为空，");
				throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空", new Exception());
			}
			GoodsAttributeManager goodsAttributeManagerCheck=baseService.getById(id);
			if(goodsAttributeManagerCheck==null) {
				logger.error("请求参数异常，");
				throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能异常", new Exception());
			}
			//上架
			if(status.equals(DBDictionaryEnumManager.user_status_0.getkey())) {
				if(goodsAttributeManagerCheck.getStatus()==Integer.parseInt(DBDictionaryEnumManager.user_status_1.getkey())) {
					GoodsAttributeManager goodsAttributeManager = new GoodsAttributeManager();
					goodsAttributeManager.setId(Long.parseLong(id));
					goodsAttributeManager.setStatus(Integer.parseInt(status));
					baseService.updateById(goodsAttributeManager);
					result.success("修改成功！");
				}
				else {
					logger.error("不为禁用状态，不可启用！");
					result.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
					result.error("不为禁用状态，不可启用！");
				}
			}
			//下架
			else if(status.equals(DBDictionaryEnumManager.user_status_1.getkey())) {
				if(goodsAttributeManagerCheck.getStatus()==Integer.parseInt(DBDictionaryEnumManager.user_status_0.getkey())) {
					GoodsAttributeManager goodsAttributeManager = new GoodsAttributeManager();
					goodsAttributeManager.setId(Long.parseLong(id));
					goodsAttributeManager.setStatus(Integer.parseInt(status));
					baseService.updateById(goodsAttributeManager);
					result.success("修改成功！");
				}
				else {
					logger.error("不为启用状态，不可禁用！");
					result.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
					result.error("不为启用状态，不可禁用！");
				}
			}else {
				logger.error("请求参数异常，");
				throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能异常", new Exception());
			}
		}
		return result;
	}
}