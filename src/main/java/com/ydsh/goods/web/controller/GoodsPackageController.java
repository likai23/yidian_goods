/**
 * @filename:GoodsPackageController 2019-06-12 10:08:38
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2020 戴艺辉 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.ydsh.goods.common.exception.SystemException;
import com.ydsh.goods.common.util.TextUtils;
import com.ydsh.goods.web.controller.base.AbstractController;
import com.ydsh.goods.web.entity.GoodsPackage;
import com.ydsh.goods.web.entity.GoodsPackageInfo;
import com.ydsh.goods.web.entity.GoodsPackagePlatform;
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
	* @param @param pageParam
	* @param @return
	* @return
	 */
    @RequestMapping(value = "/getPackagePages",method = RequestMethod.GET)
	@ApiOperation(value = "分页查询", notes = "分页查询返回[IPage<T>],作者：戴艺辉")
	public JsonResult<IPage<Map<String,Object>>> getPackagePages(PageParam<GoodsPackage> pageParam){
		JsonResult<IPage<Map<String,Object>>> returnPage=new JsonResult<IPage<Map<String,Object>>>();
		Page<GoodsPackage> page=new Page<GoodsPackage>(pageParam.getPageNum(),pageParam.getPageSize());
		QueryWrapper<GoodsPackage> queryWrapper =new QueryWrapper<GoodsPackage>();
		queryWrapper.setEntity(pageParam.getParam());
		//分页数据
		IPage<Map<String,Object>> pageData=baseService.pageMaps(page, queryWrapper);
		List<Map<String,Object>> pageDataList=pageData.getRecords();
		for(Map<String,Object> map: pageDataList) {
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
	public JsonResult<Object> savePackageGoods(@RequestBody Map<String, Object> param) {
		JsonResult<Object> result = new JsonResult<Object>();
		GoodsPackage bossGoodsPackage = new GoodsPackage();
		String gpNo = DBKeyGenerator.generatorDBKey(DBBusinessKeyTypeEnums.CP, null);
		String packageName = TextUtils.getMapForKeyToString(param, "packageName");
		String packageForshort = TextUtils.getMapForKeyToString(param, "packageForshort");
		String denomination = TextUtils.getMapForKeyToString(param, "denomination");
		String packageStatus = TextUtils.getMapForKeyToString(param, "packageStatus");
		if (TextUtils.isEmptys(packageName, denomination)) {
			logger.error("请求参数为空，");
			throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空", new Exception());
		}
		QueryWrapper<GoodsPackage> queryWrapper = new QueryWrapper<GoodsPackage>();
		queryWrapper.eq("package_name", packageName);
		Map<String, Object> var = goodsPackageService.getMap(queryWrapper);
		if (!(var == null || var.isEmpty())) {
			logger.error("套餐名称不能重复，");
			result.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			result.error("套餐名称不能重复！");
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
		@SuppressWarnings("unchecked")
		List<HashMap<String, String>> skuPackageList = (List<HashMap<String, String>>) param.get("skuPackageList");
		if (!(skuPackageList == null || skuPackageList.isEmpty())) {
			for (HashMap<String, String> var1 : skuPackageList) {
				GoodsPackageInfo bossGoodsPackageInfo = new GoodsPackageInfo();
				bossGoodsPackageInfo.setGpId(bossGoodsPackage.getId());
				bossGoodsPackageInfo.setGcsId(Long.parseLong(var1.get("skuId")));
				bossGoodsPackageInfo.setAccount(Integer.parseInt(var1.get("account")));
				goodsPackageInfoService.save(bossGoodsPackageInfo);
			}
		}
		// 套餐下的平台详情
		@SuppressWarnings("unchecked")
		List<HashMap<String, String>> platFromList = (List<HashMap<String, String>>) param.get("PackageList");
		if (!(platFromList == null || platFromList.isEmpty())) {
			for (HashMap<String, String> var2 : platFromList) {
				GoodsPackagePlatform bossGoodsPackagePlatform = new GoodsPackagePlatform();
				bossGoodsPackagePlatform.setGpId(bossGoodsPackage.getId());
				bossGoodsPackagePlatform.setPmId(Long.parseLong(var2.get("pmId")));
				bossGoodsPackagePlatform.setSkuCoverPhoto(var2.get("coverPhoto"));
				bossGoodsPackagePlatform.setSkuMainPhoto(var2.get("mainPhoto"));
				goodsPackagePlatformService.save(bossGoodsPackagePlatform);
			}
		}
		result.success("添加成功");
		return result;
	}

	/**
	 * 
	 * 1-修改套餐商品 2-修改套餐商品状态 3-审核套餐商品
	 * 
	 * @param @param  context
	 * @param @return
	 * @param @throws CoreException
	 * @return
	 */
	@RequestMapping(value = "/updatePackageGoods", method = RequestMethod.POST)
	@ApiOperation(value = "修改套餐商品", notes = "作者：戴艺辉")
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public JsonResult<Object> updatePackageGoods(@RequestBody Map<String, Object> param) {
		JsonResult<Object> result = new JsonResult<Object>();
		GoodsPackage bossGoodsPackage = new GoodsPackage();
		String updateSign = TextUtils.getMapForKeyToString(param, "updateSign");
		if (TextUtils.isEmpty(updateSign)) {
			logger.error("请求参数为空，");
			throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空", new Exception());
		}
		// 修改套餐商品
		if (updateSign.equals("updatePackage")) {
			String id = TextUtils.getMapForKeyToString(param, "id");
			String gpNo = TextUtils.getMapForKeyToString(param, "gpNo");
			String packageName = TextUtils.getMapForKeyToString(param, "packageName");
			String packageForshort = TextUtils.getMapForKeyToString(param, "packageForshort");
			String denomination = TextUtils.getMapForKeyToString(param, "denomination");
			String packageStatus = TextUtils.getMapForKeyToString(param, "packageStatus");
			if (TextUtils.isEmptys(id, gpNo, packageName, denomination)) {
				logger.error("请求参数为空，");
				throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空", new Exception());
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
			@SuppressWarnings("unchecked")
			List<HashMap<String, Object>> skuPackageList = (List<HashMap<String, Object>>) param.get("skuPackageList");
			if (!(skuPackageList == null || skuPackageList.isEmpty())) {
				for (HashMap<String, Object> var2 : skuPackageList) {
					GoodsPackageInfo bossGoodsPackageInfo = new GoodsPackageInfo();
					bossGoodsPackageInfo.setGpId(bossGoodsPackage.getId());
					bossGoodsPackageInfo.setGcsId(Long.parseLong(String.valueOf(var2.get("skuId"))));
					bossGoodsPackageInfo.setAccount(Integer.parseInt(String.valueOf(var2.get("account"))));
					goodsPackageInfoService.save(bossGoodsPackageInfo);
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
			@SuppressWarnings("unchecked")
			List<HashMap<String, Object>> platFromList = (List<HashMap<String, Object>>) param.get("PackageList");
			if (!(platFromList == null || platFromList.isEmpty())) {
				for (HashMap<String, Object> var2 : platFromList) {
					GoodsPackagePlatform bossGoodsPackagePlatform = new GoodsPackagePlatform();
					bossGoodsPackagePlatform.setGpId(bossGoodsPackage.getId());
					bossGoodsPackagePlatform.setPmId(Long.parseLong(String.valueOf(var2.get("pmId"))));
					bossGoodsPackagePlatform.setSkuCoverPhoto(String.valueOf(var2.get("coverPhoto")));
					bossGoodsPackagePlatform.setSkuMainPhoto(String.valueOf(var2.get("mainPhoto")));
					goodsPackagePlatformService.save(bossGoodsPackagePlatform);
				}
			}
			result.success("修改成功");
		}
		// 修改套餐商品状态
		else if (updateSign.equals("updatePackageStatus")) {
			String id = TextUtils.getMapForKeyToString(param, "id");
			String packageStatus = TextUtils.getMapForKeyToString(param, "packageStatus");
			if (TextUtils.isEmptys(id, packageStatus)) {
				logger.error("请求参数为空，");
				throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空", new Exception());
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
				} else {
					logger.error("状态不为下架，不可更改为上架！");
					result.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
					result.error("状态不为下架，不可更改为上架！");
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
				} else {
					logger.error("状态不为上架，不可更改为上架！");
					result.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
					result.error("状态不为上架，不可更改为上架！");
				}
			}
			// 作废
			else if (packageStatus.equals(DBDictionaryEnumManager.goods_2.getkey())) {
				if (goodsPackageCheck.getPackageStatus().equals(DBDictionaryEnumManager.goods_2.getkey())) {
					logger.error("已作废，不可重复操作！");
					result.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
					result.error("已作废，不可重复操作！");
				} else {
					GoodsPackage goodsPackage = new GoodsPackage();
					goodsPackage.setId(Long.parseLong(id));
					goodsPackage.setPackageStatus(packageStatus);
					baseService.updateById(goodsPackage);
					result.setCode(SuccessCode.SYS_SUCCESS.getCode());
					result.success("修改成功");
				}
			} else {
				logger.error("请求参数异常，");
				throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "请求参数异常", new Exception());
			}
		}
		// 审核
		else if (updateSign.equals("reviewPackage")) {
			String id = TextUtils.getMapForKeyToString(param, "id");
			String reviewStatus = TextUtils.getMapForKeyToString(param, "reviewStatus");
			String reviewRemarks = TextUtils.getMapForKeyToString(param, "reviewRemarks");
			if (TextUtils.isEmptys(id, reviewStatus, reviewRemarks)) {
				logger.error("请求参数为空，");
				throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空", new Exception());
			}
			GoodsPackage goodsPackageCheck = baseService.getById(id);
			if (goodsPackageCheck == null) {
				logger.error("请求参数为空，");
				throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空", new Exception());
			}
			if (!(goodsPackageCheck.getReviewStatus().equals(DBDictionaryEnumManager.review_0.getkey()))) {
				logger.error("不为待审核状态，不允许审核");
				result.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				result.error("不为待审核状态，不允许审核");
			} else {
				GoodsPackage goodsPackage = new GoodsPackage();
				goodsPackage.setId(Long.parseLong(id));
				goodsPackage.setReviewStatus(reviewStatus);
				goodsPackage.setReviewRemarks(reviewRemarks);
				baseService.updateById(goodsPackage);
				result.success("审核成功");
			}
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
	public JsonResult<Object> lookPackageGoods(@RequestBody Map<String, Object> param) {
		JsonResult<Object> result = new JsonResult<Object>();
		// 套餐id
		String id = TextUtils.getMapForKeyToString(param, "id");
		// 修改进入 还是 查看进入: 修改进入值为1，查看进入值为2
		String lookSign = TextUtils.getMapForKeyToString(param, "lookSign");
		if (TextUtils.isEmptys(id, lookSign)) {
			logger.error("请求参数为空，");
			throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空", new Exception());
		}
		// 查看天气套餐商品
		if (lookSign.equals("lookPackageGoods")) {
			QueryWrapper<GoodsPackage> queryWrapper = new QueryWrapper<GoodsPackage>();
			queryWrapper.eq("id", id);
			Map<String, Object> goodsPackage = baseService.getMap(queryWrapper);
			if (goodsPackage == null) {
				logger.error("请求参数异常，");
				throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "请求参数异常", new Exception());
			}
			QueryWrapper<GoodsPackageInfo> queryWrapper1 = new QueryWrapper<GoodsPackageInfo>();
			queryWrapper1.eq("gp_id", id);
			List<Map<String, Object>> packageInfoList = goodsPackageInfoService.listMaps(queryWrapper1);
			QueryWrapper<GoodsPackagePlatform> queryWrapper2 = new QueryWrapper<GoodsPackagePlatform>();
			queryWrapper.eq("gp_id", id);
			List<Map<String, Object>> packagePlatformList = goodsPackagePlatformService.listMaps(queryWrapper2);
			goodsPackage.put("packageInfoList", packageInfoList);
			goodsPackage.put("packagePlatformList", packagePlatformList);
			result.success("查询成功");
			result.setData(goodsPackage);
		}
		// 修改进入查看商品
		if (lookSign.equals("lookPackageGoodsWtihStatus")) {
			QueryWrapper<GoodsPackage> queryWrapper = new QueryWrapper<GoodsPackage>();
			queryWrapper.eq("id", id);
			Map<String, Object> goodsPackage = baseService.getMap(queryWrapper);
			if (goodsPackage == null) {
				logger.error("请求参数异常，");
				throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "请求参数异常", new Exception());
			}
			if (goodsPackage.get("review_status").equals(DBDictionaryEnumManager.review_0.getkey())
					|| goodsPackage.get("review_status").equals(DBDictionaryEnumManager.review_2.getkey())) {
				QueryWrapper<GoodsPackageInfo> queryWrapper1 = new QueryWrapper<GoodsPackageInfo>();
				queryWrapper1.eq("gp_id", id);
				List<Map<String, Object>> packageInfoList = goodsPackageInfoService.listMaps(queryWrapper1);
				QueryWrapper<GoodsPackagePlatform> queryWrapper2 = new QueryWrapper<GoodsPackagePlatform>();
				queryWrapper.eq("gp_id", id);
				List<Map<String, Object>> packagePlatformList = goodsPackagePlatformService.listMaps(queryWrapper2);
				goodsPackage.put("packageInfoList", packageInfoList);
				goodsPackage.put("packagePlatformList", packagePlatformList);
				result.success("查询成功");
				result.setData(goodsPackage);
			} else {
				logger.error("不为待审核或审核不通过状态，不可修改，");
				result.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				result.error("不为待审核或审核不通过状态，不可修改！");
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