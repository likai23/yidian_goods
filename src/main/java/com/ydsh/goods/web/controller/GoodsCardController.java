/**
 * @filename:GoodsCardController 2019-06-12 10:08:37
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2020 戴艺辉 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.ydsh.goods.common.exception.SystemException;
import com.ydsh.goods.common.util.TextUtils;
import com.ydsh.goods.web.controller.base.AbstractController;
import com.ydsh.goods.web.entity.GoodsCard;
import com.ydsh.goods.web.entity.GoodsCardSku;
import com.ydsh.goods.web.entity.GoodsCardSkuPlatforminfo;
import com.ydsh.goods.web.entity.GoodsCategory;
import com.ydsh.goods.web.entity.dto.GoodsCardAndSkuDto;
import com.ydsh.goods.web.entity.dto.LookOrUpdateTakeInGoodsCardDto;
import com.ydsh.goods.web.service.GoodsCardService;
import com.ydsh.goods.web.service.GoodsCardSkuPlatforminfoService;
import com.ydsh.goods.web.service.GoodsCardSkuService;
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
 * 说明： 卡券商品表API接口层
 * </P>
 * 
 * @version: V1.0
 * @author: 戴艺辉
 *
 */
@Api(description = "卡券商品表", value = "卡券商品表")
@RestController
@RequestMapping("/goodsCard")
@Slf4j
public class GoodsCardController extends AbstractController<GoodsCardService, GoodsCard> {


	private static Logger logger = LoggerFactory.getLogger(GoodsCardController.class);


	@Autowired
	private GoodsCardService goodsCardService;
	@Autowired
	private GoodsCardSkuService goodsCardSkuService;
	@Autowired
	private GoodsCategoryService goodsCategoryService;
	@Autowired
	private GoodsCardSkuPlatforminfoService goodsCardSkuPlatforminfoService;

	/**
	 * @explain 分页条件查询用户
	 * @param pageParam
	 * @return JsonResult
	 * @author 戴艺辉
	 * @time 2019-06-12 10:08:37
	 */
	@RequestMapping(value = "/getCardAndSKUPages", method = RequestMethod.GET)
	@ApiOperation(value = "卡券商品分页查询", notes = "分页查询返回[IPage<T>],作者：戴艺辉")
	public JsonResult<IPage<Map<String, Object>>> getCardAndSKUPages(@RequestBody PageParam<GoodsCardAndSkuDto> pageParam) {
		JsonResult<IPage<Map<String, Object>>> returnPage = new JsonResult<IPage<Map<String, Object>>>();
		if(pageParam.getPageSize()>500) {
			logger.error("分页最大限制500，" +pageParam);
			returnPage.error("分页最大限制500");
			return returnPage;
		}
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageParam.getPageNum(), pageParam.getPageSize());
		QueryWrapper<GoodsCardAndSkuDto> queryWrapper =new QueryWrapper<GoodsCardAndSkuDto>();
		queryWrapper.setEntity(pageParam.getParam());
		if(queryWrapper.getEntity()==null) {
			queryWrapper.setEntity(new GoodsCardAndSkuDto());
		}
		// 分页数据
		Page<Map<String, Object>> pageData = goodsCardService.selectCardAndSKUPage(page, queryWrapper.getEntity());
		result.success("添加成功");
		returnPage.success(pageData);

		return returnPage;
	}

	/**
	 * 
	 ** 保存商品和sku
	 *
	 * @param @param  entity
	 * @param @return
	 * @return
	 */
	@RequestMapping(value = "/saveCardWithSku", method = RequestMethod.POST)
	@ApiOperation(value = "添加", notes = "作者：戴艺辉")
	public JsonResult<Object> saveCardWithSku(@RequestBody GoodsCardAndSkuDto param) {
		JsonResult<Object> result = new JsonResult<Object>();
		String goodName = param.getGoodName();
		String goodForshort =param.getGoodForshort();
		String goodAttribute =param.getGoodAttribute(); 
		String goodType =param.getGoodType(); 
		String goodProperty =param.getGoodProperty(); 
		String goodShape =param.getGoodShape();
		String goodCategoryId =param.getGoodCategoryId();
		String goodStatus =param.getGoodStatus();
		if (TextUtils.isEmptys(goodName, goodAttribute, goodType, goodProperty, goodShape, goodCategoryId)) {
			logger.error("请求参数为空，" + param);
			throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空", new Exception());
		}
		GoodsCard bossGoodsCard = new GoodsCard();
		GoodsCategory bossGoodsCategory = goodsCategoryService.getById(goodCategoryId);
		if (bossGoodsCategory == null) {
			logger.error("类目编码不存在，" + param);
			result.error("类目编码不存在！");
			return result;
		}
		QueryWrapper<GoodsCard> queryWrapper = new QueryWrapper<GoodsCard>();
		queryWrapper.eq("goodName", goodName);
		Map<String, Object> checkName = baseService.getMap(queryWrapper);
		if(checkName!=null) {
			logger.error("商品名称不允许重复，" + param);
			result.error("商品名称不允许重复！");
			return result;
		}
		// G+10+3位类目编码+四位数字递增
		String gcNo = DBKeyGenerator.generatorDBKey(DBBusinessKeyTypeEnums.G10, bossGoodsCategory.getCategoryId());
		bossGoodsCard.setGcNo(gcNo);
		bossGoodsCard.setGoodName(goodName);
		bossGoodsCard.setGoodForshort(goodForshort);
		bossGoodsCard.setGoodAttribute(goodAttribute);
		bossGoodsCard.setGoodType(goodType);
		bossGoodsCard.setGoodProperty(goodProperty);
		bossGoodsCard.setGoodShape(goodShape);
		bossGoodsCard.setGoodCategoryId(goodCategoryId);
		bossGoodsCard.setGoodStatus(goodStatus);
		// 待审核状态
		bossGoodsCard.setReviewStatus(DBDictionaryEnumManager.review_0.getkey());
		// 保存卡券商品主表
		baseService.save(bossGoodsCard);
		// 保存卡券sku
		List<Map<String, Object>> bossGoodsCardSkuMap = (List<Map<String, Object>>) param.getSkuCardList();
		// 保存sku和平台图片详情
		if (!(bossGoodsCardSkuMap == null || bossGoodsCardSkuMap.isEmpty())) {
			// 保存卡券sku详情
			for (int i = 0; i < bossGoodsCardSkuMap.size(); i++) {
				HashMap<String, Object> map = (HashMap<String, Object>) bossGoodsCardSkuMap.get(i);
				GoodsCardSku bossGoodsCardSku = new GoodsCardSku();
				bossGoodsCardSku.setGcId(bossGoodsCard.getId());
				String skuId = gcNo + DBKeyGenerator.generatorDBKey(DBBusinessKeyTypeEnums.SKU, "");
				bossGoodsCardSku.setGcsNo(skuId);
				bossGoodsCardSku.setSkuName((String) map.get("skuName"));
				bossGoodsCardSku.setGaaId((String) map.get("gaaId"));
				bossGoodsCardSku.setSdId((String) map.get("sdId"));
				goodsCardSkuService.save(bossGoodsCardSku);
				// 级联保存到---供应价管理（供应商和商品的关系）
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> skuCardPlatformInfoList = (List<Map<String, Object>>) map
						.get("skuCardPlatformInfoList");
				if (!skuCardPlatformInfoList.isEmpty()) {
					for (int j = 0; j < skuCardPlatformInfoList.size(); j++) {
						HashMap<String, Object> Infomap = (HashMap<String, Object>) skuCardPlatformInfoList.get(j);
						GoodsCardSkuPlatforminfo bossGoodsCardskuPlatformInfo = new GoodsCardSkuPlatforminfo();
						bossGoodsCardskuPlatformInfo.setGcsId(bossGoodsCardSku.getId());
						bossGoodsCardskuPlatformInfo.setSkuCoverPhoto((String) Infomap.get("skuCoverPhoto"));
						bossGoodsCardskuPlatformInfo.setSkuMainPhoto((String) Infomap.get("skuMainPhoto"));
						bossGoodsCardskuPlatformInfo.setSkuDesc((String) Infomap.get("skuDesc"));
						bossGoodsCardskuPlatformInfo.setPmId(Long.parseLong((String) Infomap.get("pmId")));
						goodsCardSkuPlatforminfoService.save(bossGoodsCardskuPlatformInfo);
					}
				}
			}
		}
		result.success("添加成功");
		return result;
	}

	/**
	 * 
	 ** 查看商品和sku
	 *
	 * @param @param  entity
	 * @param @return
	 * @return
	 */
	@RequestMapping(value = "/getCardWithSkuById", method = RequestMethod.POST)
	@ApiOperation(value = "查看商品和sku", notes = "作者：戴艺辉")
	public JsonResult<Object> lookCardGoods(@RequestBody LookOrUpdateTakeInGoodsCardDto param) {
		JsonResult<Object> result = new JsonResult<Object>();
		// 商品id
		String bgcId = String.valueOf(param.getGcId());
		// sku id
		String bgcsId = String.valueOf(param.getGcsId());
		// 修改进入值为lookSignWithStatus，查看进入值为lookSign
		String getSign = param.getGetSign();
		if (TextUtils.isEmptys(bgcId, bgcsId, getSign)) {
			logger.error("请求参数为空，" + param);
			throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空", new Exception());
		}
		// 查看
		if (getSign.equals("lookSign")) {
			QueryWrapper<GoodsCard> queryWrapper = new QueryWrapper<GoodsCard>();
			queryWrapper.eq("id", bgcId);
			Map<String, Object> bossGoodsCard = baseService.getMap(queryWrapper);
			if (bossGoodsCard == null) {
				logger.error("商品不存在！");
				result.error("商品不存在！");
				return result;
			}
			QueryWrapper<GoodsCardSku> queryWrapperSku = new QueryWrapper<GoodsCardSku>();
			queryWrapperSku.eq("id", bgcsId);
			List<Map<String, Object>> skulist = goodsCardSkuService.listMaps(queryWrapperSku);
			for (Map<String, Object> map : skulist) {
				String id = String.valueOf(map.get("id"));
				QueryWrapper<GoodsCardSkuPlatforminfo> goodsCardSkuPlatforminfo = new QueryWrapper<GoodsCardSkuPlatforminfo>();
				goodsCardSkuPlatforminfo.eq("gcs_id", id);
				List<Map<String, Object>> goodsCardSkuPlatforminfoList = goodsCardSkuPlatforminfoService
						.listMaps(goodsCardSkuPlatforminfo);
				map.put("skuCardPlatformInfoList", goodsCardSkuPlatforminfoList);
			}
			bossGoodsCard.put("skuCardList", skulist);
			result.success("查询成功");
			result.setData(bossGoodsCard);
            return result;
		}
		// 修改时进入查询
		else if (getSign.equals("lookSignWithStatus")) {
			QueryWrapper<GoodsCard> queryWrapper = new QueryWrapper<GoodsCard>();
			queryWrapper.eq("id", bgcId);
			Map<String, Object> bossGoodsCard = baseService.getMap(queryWrapper);
			if (bossGoodsCard == null) {
				logger.error("商品不存在！");
				result.error("商品不存在！");
				return result;
			}
			String reviewStatus = (String) bossGoodsCard.get("review_status");
			if (reviewStatus.equals(DBDictionaryEnumManager.review_0.getkey())
					|| reviewStatus.equals(DBDictionaryEnumManager.review_2.getkey())) {
				QueryWrapper<GoodsCardSku> queryWrapperSku = new QueryWrapper<GoodsCardSku>();
				queryWrapperSku.eq("id", bgcsId);
				List<Map<String, Object>> skulist = goodsCardSkuService.listMaps(queryWrapperSku);
				for (Map<String, Object> map : skulist) {
					String id = String.valueOf(map.get("id"));
					QueryWrapper<GoodsCardSkuPlatforminfo> goodsCardSkuPlatforminfo = new QueryWrapper<GoodsCardSkuPlatforminfo>();
					goodsCardSkuPlatforminfo.eq("gcs_id", id);
					List<Map<String, Object>> goodsCardSkuPlatforminfoList = goodsCardSkuPlatforminfoService
							.listMaps(goodsCardSkuPlatforminfo);
					map.put("skuCardPlatformInfoList", goodsCardSkuPlatforminfoList);
				}
				bossGoodsCard.put("skuCardList", skulist);
				result.success("查询成功");
				result.setData(bossGoodsCard);
				return result;
			} else {
				result.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				result.error("不为待审核或审核不通过状态，不允许修改！");
				return result;
			}

		}
		return result;
	}

	/**
	 * 
	 * 修改卡券商品
	 *
	 * @param @param  pageParam
	 * @param @return
	 * @return
	 */
	@RequestMapping(value = "/updateGoodsCard", method = RequestMethod.POST)
	@ApiOperation(value = "修改卡券商品", notes = "分页查询返回[IPage<T>],作者：戴艺辉")
	public JsonResult<Object> updateGoodsCard(@RequestBody GoodsCardAndSkuDto param) {
		JsonResult<Object> result = new JsonResult<Object>();
		String updateSign = param.getUpdateSign();
		if (TextUtils.isEmpty(updateSign)) {
			logger.error("请求参数为空，");
			throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空", new Exception());
		}
		// 修改卡券商品基本信息
		if (updateSign.equals("updateGoodsCard")) {
			String id = String.valueOf(param.getGcId());
			String goodStatus = param.getGoodStatus();
//			String bgemId = TextUtils.getMapForKeyToString(param, "bgemId");
			if (TextUtils.isEmptys(id)) {
				logger.error("请求参数为空，" + param);
				throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空", new Exception());
			}
			GoodsCard bossGoodsCard = new GoodsCard();
			bossGoodsCard.setId(Long.parseLong(id));
			bossGoodsCard.setGoodStatus(goodStatus);
//			bossGoodsCard.setBgemId(Long.parseLong(bgemId));
			// 待审核状态
			bossGoodsCard.setReviewStatus(DBDictionaryEnumManager.review_0.getkey());
			// 更新卡券商品主表
			goodsCardService.updateById(bossGoodsCard);
			List<Map<String, Object>> bossGoodsCardSkuMapList =param.getSkuCardList();
			// 更新sku平台图片详情
			if (bossGoodsCardSkuMapList == null || bossGoodsCardSkuMapList.isEmpty()) {
				result.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				result.error("sku信息不存在");
			} else {
				// 更新卡券sku详情
				for(Map<String, Object> bossGoodsCardSkuMap:bossGoodsCardSkuMapList) {
					GoodsCardSku bossGoodsCardSku = new GoodsCardSku();
					bossGoodsCardSku.setId(Long.parseLong(String.valueOf(bossGoodsCardSkuMap.get("id"))));
					bossGoodsCardSku.setSdId(String.valueOf(bossGoodsCardSkuMap.get("sdId")));
					goodsCardSkuService.updateById(bossGoodsCardSku);
					
					QueryWrapper<GoodsCardSkuPlatforminfo> queryWrapperSku = new QueryWrapper<GoodsCardSkuPlatforminfo>();
					queryWrapperSku.eq("gcs_id", bossGoodsCardSku.getId());
					
					@SuppressWarnings("unchecked")
					List<Map<String, Object>> skuCardPlatformInfoList = (List<Map<String, Object>>) bossGoodsCardSkuMap.get("skuCardPlatformInfoList");
					if (!skuCardPlatformInfoList.isEmpty()) {
						for (int j = 0; j < skuCardPlatformInfoList.size(); j++) {
							HashMap<String, Object> Infomap = (HashMap<String, Object>) skuCardPlatformInfoList.get(j);
							GoodsCardSkuPlatforminfo bossGoodsCardskuPlatformInfo = new GoodsCardSkuPlatforminfo();
							bossGoodsCardskuPlatformInfo.setId(Long.parseLong(String.valueOf(Infomap.get("id"))));
							bossGoodsCardskuPlatformInfo.setSkuCoverPhoto(String.valueOf(Infomap.get("skuCoverPhoto")));
							bossGoodsCardskuPlatformInfo.setSkuMainPhoto(String.valueOf(Infomap.get("skuMainPhoto")));
							bossGoodsCardskuPlatformInfo.setSkuDesc(String.valueOf(Infomap.get("skuDesc")));
							bossGoodsCardskuPlatformInfo.setPmId(Long.parseLong(String.valueOf(Infomap.get("pmId"))));
							goodsCardSkuPlatforminfoService.updateById(bossGoodsCardskuPlatformInfo);
						}
					}
				}
			
			}
			result.success("修改成功");
		}
		// 上架，下架，作废
		else if (updateSign.equals("updateGoodsCardWithStatus")) {
			String id = String.valueOf(param.getGcId());
			String status = String.valueOf(param.getStatus());
			if (TextUtils.isEmptys(id, status)) {
				logger.error("请求参数为空，");
				throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空", new Exception());
			}
			GoodsCard goodsCardCheck = goodsCardService.getById(id);
			if (goodsCardCheck == null) {
				logger.error("商品不存在！");
				result.error("商品不存在！");
				return result;
			}
			// 上架,,只能修改下架的
			if (status.equals(DBDictionaryEnumManager.goods_0.getkey())) {
				if (goodsCardCheck.getGoodStatus().equals(DBDictionaryEnumManager.goods_1.getkey())) {
					GoodsCard goodsCard = new GoodsCard();
					goodsCard.setId(Long.parseLong(id));
					goodsCard.setGoodStatus(status);
					goodsCardService.updateById(goodsCard);
					result.success("修改成功");
				} else {
					result.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
					result.error("修改失败，状态不为下架不能上架");
					return result;
				}
			}
			// 下架,,只能修改上架的
			else if (status.equals(DBDictionaryEnumManager.goods_1.getkey())) {
				if (goodsCardCheck.getGoodStatus().equals(DBDictionaryEnumManager.goods_0.getkey())) {
					GoodsCard goodsCard = new GoodsCard();
					goodsCard.setId(Long.parseLong(id));
					goodsCard.setGoodStatus(status);
					goodsCardService.updateById(goodsCard);
					result.success("修改成功");
				} else {
					result.error("修改失败，状态不为上架不能下架");
					return result;
				}
			}
			// 作废,,只能修改非作废的
			else if (status.equals(DBDictionaryEnumManager.goods_2.getkey())) {
				if (goodsCardCheck.getGoodStatus().equals(DBDictionaryEnumManager.goods_2.getkey())) {
					result.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
					result.error("修改失败，状态不为下架不能启用");
					return result;
				} else {
					GoodsCard goodsCard = new GoodsCard();
					goodsCard.setId(Long.parseLong(id));
					goodsCard.setGoodStatus(status);
					goodsCardService.updateById(goodsCard);
					result.success("修改成功");
				}
			} else {
				result.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				result.error("修改失败，参数异常");
				return result;
			}
		}
		// 审核
		else if (updateSign.equals("reviewGoodsCard")) {
			String id = String.valueOf(param.getGcId()); 
			String reviewStatus =param.getReviewStatus();
			String reviewRemarks = param.getReviewRemarks();
			if (TextUtils.isEmptys(id, reviewStatus, reviewRemarks)) {
				logger.error("请求参数为空，");
				throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空", new Exception());
			}
			GoodsCard goodsCardCheck = goodsCardService.getById(id);
			if (goodsCardCheck == null) {
				logger.error("商品不存在！");
				result.error("商品不存在！");
				return result;
			}
			if (goodsCardCheck.getReviewStatus().equals(DBDictionaryEnumManager.review_0.getkey())) {
				GoodsCard goodsCard = new GoodsCard();
				goodsCard.setId(Long.parseLong(id));
				goodsCard.setReviewStatus(reviewStatus);
				goodsCard.setReviewRemarks(reviewRemarks);
				goodsCardService.updateById(goodsCard);
				result.success("审核成功");
			} else {
				logger.error("不为禁用状态，不可启用！");
				result.error("不为禁用状态，不可启用！");
				return result;
			}

		}
		return result;
	}

}