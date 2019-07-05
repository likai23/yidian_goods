/**
 * @filename:GoodsCategoryController 2019-06-12 10:08:38
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2020 戴艺辉 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import com.ydsh.goods.web.entity.GoodsCategory;
import com.ydsh.goods.web.entity.dto.GoodsCategoryDto;
import com.ydsh.goods.web.service.GoodsCategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 自定义方法写在这里
 * </p>
 * 
 * <p>
 * 说明： 商品类目管理API接口层
 * </P>
 * 
 * @version: V1.0
 * @author: 戴艺辉
 *
 */
@Api(description = "商品类目管理", value = "商品类目管理")
@RestController
@RequestMapping("/goodsCategory")
@Slf4j
public class GoodsCategoryController extends AbstractController<GoodsCategoryService, GoodsCategory> {

	@Autowired
	private GoodsCategoryService goodsCategoryService;

	/**
	 * @explain 分页条件查询用户
	 * @param pageParam
	 * @return JsonResult
	 * @author 戴艺辉
	 * @time 2019-06-12 10:08:37
	 */
	@RequestMapping(value = "/getGoodsCategoryPages", method = RequestMethod.GET)
	@ApiOperation(value = "分页查询商品类目管理", notes = "分页查询返回[IPage<T>],作者：")
	public JsonResult<IPage<GoodsCategoryDto>> getGoodsCategoryPages(PageParam<Map<String, Object>> pageParam) {
		if (pageParam.getPageSize() > 500) {
			log.error("分页最大限制500，" + pageParam);
			result.error("分页最大限制500");
		}
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageParam.getPageNum(), pageParam.getPageSize());
		JsonResult<IPage<GoodsCategoryDto>> returnPage = new JsonResult<IPage<GoodsCategoryDto>>();
		Map<String, Object> queryWrapper = new HashMap<String, Object>();
		// 分页数据
		IPage<GoodsCategoryDto> pageData = goodsCategoryService.getGoodsCategoryPages(page, queryWrapper);
		result.success("添加成功");
		returnPage.success(pageData);

		return returnPage;
	}

	/**
	 * @explain 修改
	 * @param entity
	 * @return JsonResult
	 * @author 戴艺辉
	 * @time 2019-06-12 10:08:37
	 */
	@RequestMapping(value = "/updateByIdForStatus", method = RequestMethod.POST)
	@ApiOperation(value = "根据id启用或禁用", notes = "作者：戴艺辉")
	public JsonResult<Object> updateByIdForStatus(@RequestBody GoodsCategory entity) {
		JsonResult<Object> result = new JsonResult<Object>();
		String id = String.valueOf(entity.getId());
		String status = String.valueOf(entity.getStatus());
		if (TextUtils.isEmptys(id, status)) {
			log.error("请求参数为空，");
			throw new BizException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空");
		}
		GoodsCategory goodsCategoryCheck = baseService.getById(id);
		if (goodsCategoryCheck == null) {
			log.error("请求参数异常，");
			throw new BizException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "请求参数异常");
		}
		// 启用
		if (status.equals(DBDictionaryEnumManager.user_status_0.getkey())) {
			if (String.valueOf(goodsCategoryCheck.getStatus()).equals(DBDictionaryEnumManager.user_status_1.getkey())) {
				baseService.updateById(entity);
				result.success("启用成功！");
				return result;
			} else {
				log.error("不是禁用状态不允许启用，");
				result.error("不是禁用状态不允许启用！");
				return result;
			}
		}
		// 禁用
		else if (status.equals(DBDictionaryEnumManager.user_status_1.getkey())) {
			if (String.valueOf(goodsCategoryCheck.getStatus()).equals(DBDictionaryEnumManager.user_status_0.getkey())) {
				baseService.updateById(entity);
				result.success("禁用成功！");
				return result;
			} else {
				log.error("不是启用状态不允许禁用，");
				result.error("不是启用状态不允许禁用！");
				return result;
			}
		} else {
			log.error("请求参数异常，");
			throw new BizException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "请求参数异常");
		}
	}

	/**
	 * @explain 得到树形结构列表
	 * @param entity
	 * @return JsonResult
	 * @author 戴艺辉
	 * @time 2019-06-12 10:08:37
	 */
	@RequestMapping(value = "/getCategoryForTree", method = RequestMethod.POST)
	@ApiOperation(value = "得到树形结构列表", notes = "作者：戴艺辉")
	public JsonResult<Object> getCategoryForTree() {
		JsonResult<Object> result = new JsonResult<Object>();
		try {
			List<Map<String, Object>> treeMap = ListToTree(null, baseService.listMaps());
			result.setData(treeMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static List<Map<String, Object>> ListToTree(Integer id, List<Map<String, Object>> listData) throws Exception {
		List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
		Iterator<Map<String, Object>> it = listData.iterator();
		while (it.hasNext()) {
			Map<String, Object> map = (Map<String, Object>) it.next();
			if (map.get("parent_id") == id || String.valueOf(map.get("parent_id")).equals(String.valueOf(id))) {
				treeList.add(map);
				it.remove();
			}
		}
		for (Map<String, Object> map : treeList) {
			map.put("children", ListToTree((Integer) map.get("id"), listData));
		}
		return treeList;
	}

}