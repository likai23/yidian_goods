/**
 * @filename:GoodsPackageController 2019-06-12 10:08:38
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2020 戴艺辉 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydsh.generator.common.JsonResult;
import com.ydsh.generator.common.PageParam;
import com.ydsh.goods.common.db.DBKeyGenerator;
import com.ydsh.goods.common.enums.DBBusinessKeyTypeEnums;
import com.ydsh.goods.common.enums.DBDictionaryEnumManager;
import com.ydsh.goods.common.enums.ErrorCode;
import com.ydsh.goods.common.enums.SuccessCode;
import com.ydsh.goods.common.exception.BizException;
import com.ydsh.goods.common.util.TextUtils;
import com.ydsh.goods.web.controller.base.AbstractController;
import com.ydsh.goods.web.entity.GoodsPackage;
import com.ydsh.goods.web.entity.GoodsPackageInfo;
import com.ydsh.goods.web.entity.GoodsPackagePlatform;
import com.ydsh.goods.web.entity.dto.GoodsPackageAndSkuDto;
import com.ydsh.goods.web.entity.dto.GoodsPackageDto;
import com.ydsh.goods.web.entity.dto.LookAndUpdateInGoodsPackageDto;
import com.ydsh.goods.web.entity.dto.reviewGoodsPackageDto;
import com.ydsh.goods.web.entity.dto.updateGoodsPackageStatusDto;
import com.ydsh.goods.web.service.GoodsPackageInfoService;
import com.ydsh.goods.web.service.GoodsPackagePlatformService;
import com.ydsh.goods.web.service.GoodsPackageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 自定义方法写在这里
 * </p>
 * 
 * <p>
 * 说明： 套餐商品管理API接口层
 * </P>
 * 
 * @version: V1.0
 * @author: 戴艺辉
 *
 */
@Api(description = "套餐商品管理", value = "套餐商品管理")
@RestController
@RequestMapping("/goodsPackage")
@Slf4j
public class GoodsPackageController extends AbstractController<GoodsPackageService, GoodsPackage> {

	@Autowired
	private GoodsPackageService goodsPackageService;
	@Autowired
	private GoodsPackageInfoService goodsPackageInfoService;
	@Autowired
	private GoodsPackagePlatformService goodsPackagePlatformService;

	/**
	 * 
	 * 套餐商品分页查询
	 *
	 * @param @param  pageParam
	 * @param @return
	 * @return
	 */
	@RequestMapping(value = "/getPackagePages", method = RequestMethod.GET)
	@ApiOperation(value = "分页查询", notes = "分页查询返回[IPage<T>],作者：戴艺辉")
	public JsonResult<IPage<Map<String, Object>>> getPackagePages(PageParam<GoodsPackage> pageParam) {
		JsonResult<IPage<Map<String, Object>>> returnPage = new JsonResult<IPage<Map<String, Object>>>();
		Page<GoodsPackage> page = new Page<GoodsPackage>(pageParam.getPageNum(), pageParam.getPageSize());
		if (pageParam.getPageSize() > 500) {
			log.error("分页最大限制500，" + pageParam);
			returnPage.error("分页最大限制500");
			return returnPage;
		}
		QueryWrapper<GoodsPackage> queryWrapper = new QueryWrapper<GoodsPackage>();
		queryWrapper.setEntity(pageParam.getParam());
		// 分页数据
		IPage<Map<String, Object>> pageData = baseService.pageMaps(page, queryWrapper);
		List<Map<String, Object>> pageDataList = pageData.getRecords();
		for (Map<String, Object> map : pageDataList) {
			QueryWrapper<GoodsPackageInfo> var = new QueryWrapper<GoodsPackageInfo>();
			queryWrapper.eq("gp_id", map.get("id"));
			List<Map<String, Object>> list1 = goodsPackageInfoService.listMaps(var);
			map.put("商品数", list1.size());
		}
		returnPage.success(pageData);

		return returnPage;
	}

	/**
	 * 
	 * 套餐商品和关联sku分页查询
	 *
	 * @param @param  pageParam
	 * @param @return
	 * @return
	 */
	@RequestMapping(value = "/getPackageAndSkuPages", method = RequestMethod.GET)
	@ApiOperation(value = "套餐商品和关联sku分页查询", notes = "分页查询返回[IPage<T>],作者：戴艺辉")
	public JsonResult<Object> getPackageAndSkuPages(@RequestBody PageParam<GoodsPackageAndSkuDto> pageParam) {
		JsonResult<Object> returnPage = new JsonResult<Object>();
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageParam.getPageNum(), pageParam.getPageSize());
		if (pageParam.getPageSize() > 500) {
			log.error("分页最大限制500，" + pageParam);
			returnPage.error("分页最大限制500");
			return returnPage;
		}
		QueryWrapper<GoodsPackageAndSkuDto> queryWrapper = new QueryWrapper<GoodsPackageAndSkuDto>();
		queryWrapper.setEntity(pageParam.getParam());
		if (queryWrapper.getEntity() == null) {
			queryWrapper.setEntity(new GoodsPackageAndSkuDto());
		}
		// 分页数据
		IPage<GoodsPackageAndSkuDto> pageData = baseService.getPackageAndSkuPages(page, queryWrapper.getEntity());

		returnPage.success(pageData);

		return returnPage;
	}

	/**
	 * 
	 * 保存套餐商品
	 *
	 * @param @param  context
	 * @param @return
	 * @param @throws CoreException
	 * @return
	 */
	@RequestMapping(value = "/savePackageGoods", method = RequestMethod.POST)
	@ApiOperation(value = "添加套餐商品", notes = "作者：戴艺辉")
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public JsonResult<Object> savePackageGoods(@RequestBody GoodsPackageDto param) {
		JsonResult<Object> result = new JsonResult<Object>();
		GoodsPackage bossGoodsPackage = new GoodsPackage();
		String gpNo = DBKeyGenerator.generatorDBKey(DBBusinessKeyTypeEnums.CP, null);
		String packageName = param.getPackageName();
		String packageForshort = param.getPackageForshort();
		String denomination = String.valueOf(param.getDenomination());
		String packageStatus = param.getPackageStatus();
		if (TextUtils.isEmptys(packageName, denomination)) {
			log.error("请求参数为空，");
			throw new BizException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空");
		}
		QueryWrapper<GoodsPackage> queryWrapper = new QueryWrapper<GoodsPackage>();
		queryWrapper.eq("package_name", packageName);
		Map<String, Object> var = goodsPackageService.getMap(queryWrapper);
		if (!(var == null || var.isEmpty())) {
			log.error("套餐名称不能重复，");
			result.error("套餐名称不能重复！");
			return result;
		}
		bossGoodsPackage.setGpNo(gpNo);
		bossGoodsPackage.setPackageName(packageName);
		bossGoodsPackage.setPackageForshort(packageForshort);
		bossGoodsPackage.setDenomination(new Integer(turnForInt(denomination)));
		bossGoodsPackage.setPackageStatus(packageStatus);
		bossGoodsPackage.setReviewStatus(DBDictionaryEnumManager.review_0.getkey());
		// 保存套餐信息
		goodsPackageService.save(bossGoodsPackage);
		// 套餐下的sku
		List<GoodsPackageInfo> skuPackageList = param.getSkuPackageList();
		if (!(skuPackageList == null || skuPackageList.isEmpty())) {
			for (GoodsPackageInfo var1 : skuPackageList) {
				var1.setGpId(bossGoodsPackage.getId());
				goodsPackageInfoService.save(var1);
			}
		}
		// 套餐下的平台详情
		List<GoodsPackagePlatform> platFromList = param.getPackageList();
		if (!(platFromList == null || platFromList.isEmpty())) {
			for (GoodsPackagePlatform var2 : platFromList) {
				var2.setGpId(bossGoodsPackage.getId());
				goodsPackagePlatformService.save(var2);
			}
		}
		result.success("添加成功");
		return result;
	}

	/**
	 * 
	 * * 修改套餐商品
	 * 
	 * @param @param  context
	 * @param @return
	 * @param @throws CoreException
	 * @return
	 */
	@RequestMapping(value = "/updatePackageGoods", method = RequestMethod.POST)
	@ApiOperation(value = "修改套餐商品", notes = "作者：戴艺辉")
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public JsonResult<Object> updatePackageGoods(@RequestBody GoodsPackageDto param) {
		JsonResult<Object> result = new JsonResult<Object>();
		GoodsPackage bossGoodsPackage = new GoodsPackage();
		// 修改套餐商品
		String id = String.valueOf(param.getId());
		String gpNo = param.getGpNo();
		String packageName = param.getPackageName();
		String packageForshort = param.getPackageForshort();
		String denomination = String.valueOf(param.getDenomination());
		String packageStatus = param.getPackageStatus();
		if (TextUtils.isEmptys(id, gpNo, packageName, denomination)) {
			log.error("请求参数为空，");
			throw new BizException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空");
		}
		QueryWrapper<GoodsPackage> queryWrapperCheck = new QueryWrapper<GoodsPackage>();
		queryWrapperCheck.eq("package_name", packageName);
		Map<String, Object> var = goodsPackageService.getMap(queryWrapperCheck);
		if (!(var == null || var.isEmpty())) {
			log.error("套餐名称不能重复，");
			result.error("套餐名称不能重复！");
			return result;
		}
		bossGoodsPackage.setId(Long.parseLong(id));
		bossGoodsPackage.setGpNo(gpNo);
		bossGoodsPackage.setPackageName(packageName);
		bossGoodsPackage.setPackageForshort(packageForshort);
		bossGoodsPackage.setDenomination(new Integer(denomination));
		bossGoodsPackage.setPackageStatus(packageStatus);
		goodsPackageService.updateById(bossGoodsPackage);
		// 查询套餐id下是否有sku信息
		QueryWrapper<GoodsPackageInfo> queryWrapper = new QueryWrapper<GoodsPackageInfo>();
		queryWrapper.eq("gp_id", id);
		List<Map<String, Object>> list1 = goodsPackageInfoService.listMaps(queryWrapper);
		if (!(list1 == null || list1.isEmpty())) {
			// 物理删除
			for (Map<String, Object> map : list1) {
				goodsPackageInfoService.removeById(String.valueOf(map.get("id")));
			}
		}
		// 套餐下的sku
		List<GoodsPackageInfo> skuPackageList = param.getSkuPackageList();
		if (!(skuPackageList == null || skuPackageList.isEmpty())) {
			for (GoodsPackageInfo var2 : skuPackageList) {
				var2.setGpId(bossGoodsPackage.getId());
				goodsPackageInfoService.save(var2);
			}
		}
		QueryWrapper<GoodsPackagePlatform> queryWrapperSecond = new QueryWrapper<GoodsPackagePlatform>();
		queryWrapperSecond.eq("gp_id", id);
		List<Map<String, Object>> list2 = goodsPackagePlatformService.listMaps(queryWrapperSecond);
		if (!(list2 == null || list2.isEmpty())) {
			// 物理删除
			for (Map<String, Object> map : list2) {
				goodsPackagePlatformService.removeById(String.valueOf(map.get("id")));
			}
		}
		// 套餐下的平台详情
		List<GoodsPackagePlatform> platFromList = param.getPackageList();
		if (!(platFromList == null || platFromList.isEmpty())) {
			for (GoodsPackagePlatform var2 : platFromList) {
				var2.setGpId(bossGoodsPackage.getId());
				goodsPackagePlatformService.save(var2);
			}
		}
		result.success("修改成功");
		return result;
	}

	/**
	 * 
	 * *修改套餐商品状态
	 * 
	 * @param @param  context
	 * @param @return
	 * @param @throws CoreException
	 * @return
	 */
	@RequestMapping(value = "/updatePackageGoodsStatus", method = RequestMethod.POST)
	@ApiOperation(value = "修改套餐商品状态", notes = "作者：戴艺辉")
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public JsonResult<Object> updatePackageGoodsStatus(@RequestBody updateGoodsPackageStatusDto param) {
		JsonResult<Object> result = new JsonResult<Object>();
		String id = String.valueOf(param.getId());
		String packageStatus = param.getPackageStatus();
		if (TextUtils.isEmptys(id, packageStatus)) {
			log.error("请求参数为空，");
			throw new BizException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空");
		}
		GoodsPackage goodsPackageCheck = baseService.getById(id);
		// 上架
		if (packageStatus.equals(DBDictionaryEnumManager.goods_0.getkey())) {
			if (goodsPackageCheck.getPackageStatus().equals(DBDictionaryEnumManager.goods_1.getkey())) {
				GoodsPackage goodsPackage = new GoodsPackage();
				goodsPackage.setId(Long.parseLong(id));
				goodsPackage.setPackageStatus(packageStatus);
				baseService.updateById(goodsPackage);
				result.success("修改成功");
				return result;
			} else {
				log.error("状态不为下架，不可更改为上架！");
				result.error("状态不为下架，不可更改为上架！");
				return result;
			}
		}
		// 下架
		else if (packageStatus.equals(DBDictionaryEnumManager.goods_1.getkey())) {
			if (goodsPackageCheck.getPackageStatus().equals(DBDictionaryEnumManager.goods_0.getkey())) {
				GoodsPackage goodsPackage = new GoodsPackage();
				goodsPackage.setId(Long.parseLong(id));
				goodsPackage.setPackageStatus(packageStatus);
				baseService.updateById(goodsPackage);
				result.success("修改成功");
				return result;
			} else {
				log.error("状态不为上架，不可更改为上架！");
				result.error("状态不为上架，不可更改为上架！");
				return result;
			}
		}
		// 作废
		else if (packageStatus.equals(DBDictionaryEnumManager.goods_2.getkey())) {
			if (goodsPackageCheck.getPackageStatus().equals(DBDictionaryEnumManager.goods_2.getkey())) {
				log.error("已作废，不可重复操作！");
				result.error("已作废，不可重复操作！");
				return result;
			} else {
				GoodsPackage goodsPackage = new GoodsPackage();
				goodsPackage.setId(Long.parseLong(id));
				goodsPackage.setPackageStatus(packageStatus);
				baseService.updateById(goodsPackage);
				result.setCode(SuccessCode.SYS_SUCCESS.getCode());
				result.success("修改成功");
				return result;
			}
		} else {
			log.error("请求参数异常，");
			result.error("请求参数异常！");
			return result;
		}

	}

	/**
	 * 
	 * *审核套餐商品
	 * 
	 * @param @param  context
	 * @param @return
	 * @param @throws CoreException
	 * @return
	 */
	@RequestMapping(value = "/reviewPackageGoods", method = RequestMethod.POST)
	@ApiOperation(value = "审核套餐商品", notes = "作者：戴艺辉")
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public JsonResult<Object> reviewPackageGoods(@RequestBody reviewGoodsPackageDto param) {
		JsonResult<Object> result = new JsonResult<Object>();
		String id = String.valueOf(param.getId());
		String reviewStatus = param.getReviewStatus();
		String reviewRemarks = param.getReviewRemarks();
		if (TextUtils.isEmptys(id, reviewStatus, reviewRemarks)) {
			log.error("请求参数为空，");
			throw new BizException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空");
		}
		GoodsPackage goodsPackageCheck = baseService.getById(id);
		if (goodsPackageCheck == null) {
			log.error("请求参数为空，");
			throw new BizException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空");
		}
		if (!(goodsPackageCheck.getReviewStatus().equals(DBDictionaryEnumManager.review_0.getkey()))) {
			log.error("不为待审核状态，不允许审核");
			result.error("不为待审核状态，不允许审核");
			return result;
		} else {
			GoodsPackage goodsPackage = new GoodsPackage();
			goodsPackage.setId(Long.parseLong(id));
			goodsPackage.setReviewStatus(reviewStatus);
			goodsPackage.setReviewRemarks(reviewRemarks);
			baseService.updateById(goodsPackage);
			result.success("审核成功");
		}
		return result;
	}

	/**
	 * 
	 * 1-查看套餐商品 2-修改进入查看商品
	 * 
	 * @param @param  context
	 * @param @return
	 * @param @throws CoreException
	 * @return
	 */
	@RequestMapping(value = "/lookPackageGoods", method = RequestMethod.POST)
	@ApiOperation(value = "查看套餐商品", notes = "作者：戴艺辉")
	public JsonResult<GoodsPackageDto> lookPackageGoods(@RequestBody LookAndUpdateInGoodsPackageDto param) {
		JsonResult<GoodsPackageDto> result = new JsonResult<GoodsPackageDto>();
		// 套餐id
		String id = String.valueOf(param.getId());
		// 修改进入 还是 查看进入: 修改进入值为1，查看进入值为
		String lookSign = param.getLookSign();
		if (TextUtils.isEmptys(id, lookSign)) {
			log.error("请求参数为空，");
			throw new BizException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空");
		}
		// 查看套餐商品
		if (lookSign.equals("lookPackageGoods")) {
			QueryWrapper<GoodsPackage> queryWrapper = new QueryWrapper<GoodsPackage>();
			queryWrapper.eq("id", id);
			GoodsPackage goodsPackage = baseService.getById(id);
			if (goodsPackage == null) {
				log.error("请求参数异常，");
				throw new BizException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "请求参数异常");
			}
			GoodsPackageDto goodsPackageDto = new GoodsPackageDto();
			BeanUtils.copyProperties(goodsPackage, goodsPackageDto);
			QueryWrapper<GoodsPackageInfo> queryWrapper1 = new QueryWrapper<GoodsPackageInfo>();
			queryWrapper1.eq("gp_id", id);
			List<GoodsPackageInfo> packageInfoList = goodsPackageInfoService.list(queryWrapper1);
			QueryWrapper<GoodsPackagePlatform> queryWrapper2 = new QueryWrapper<GoodsPackagePlatform>();
			queryWrapper2.eq("gp_id", id);
			List<GoodsPackagePlatform> packagePlatformList = goodsPackagePlatformService.list(queryWrapper2);
			goodsPackageDto.setSkuPackageList(packageInfoList);
			goodsPackageDto.setPackageList(packagePlatformList);
			result.success("查询成功");
			result.setData(goodsPackageDto);
		}
		// 修改进入查看商品
		if (lookSign.equals("lookPackageGoodsWtihStatus")) {
			QueryWrapper<GoodsPackage> queryWrapper = new QueryWrapper<GoodsPackage>();
			queryWrapper.eq("id", id);
			GoodsPackage goodsPackage = baseService.getById(id);
			if (goodsPackage == null) {
				log.error("请求参数异常，");
				throw new BizException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "请求参数异常");
			}
			if (goodsPackage.getReviewStatus().equals(DBDictionaryEnumManager.review_0.getkey())
					|| goodsPackage.getReviewStatus().equals(DBDictionaryEnumManager.review_2.getkey())) {
				GoodsPackageDto goodsPackageDto = new GoodsPackageDto();
				BeanUtils.copyProperties(goodsPackage, goodsPackageDto);
				QueryWrapper<GoodsPackageInfo> queryWrapper1 = new QueryWrapper<GoodsPackageInfo>();
				queryWrapper1.eq("gp_id", id);
				List<GoodsPackageInfo> packageInfoList = goodsPackageInfoService.list(queryWrapper1);
				QueryWrapper<GoodsPackagePlatform> queryWrapper2 = new QueryWrapper<GoodsPackagePlatform>();
				queryWrapper2.eq("gp_id", id);
				List<GoodsPackagePlatform> packagePlatformList = goodsPackagePlatformService.list(queryWrapper2);
				goodsPackageDto.setSkuPackageList(packageInfoList);
				goodsPackageDto.setPackageList(packagePlatformList);
				result.success("查询成功");
				result.setData(goodsPackageDto);
			} else {
				log.error("不为待审核或审核不通过状态，不可修改，");
				result.error("不为待审核或审核不通过状态，不可修改！");
				return result;
			}
		}
		return result;
	}

	/**
	 * 
	 * 小数转成int
	 *
	 * @param @param  context
	 * @param @return
	 * @return
	 */
	public static int turnForInt(String param) {
		BigDecimal payAmount = new BigDecimal(param).multiply(new BigDecimal("10000"));
		return payAmount.intValue();
	}
}