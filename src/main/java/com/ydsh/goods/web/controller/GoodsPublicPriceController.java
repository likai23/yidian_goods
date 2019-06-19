/**
 * @filename:GoodsPublicPriceController 2019-06-12 10:08:38
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2020 戴艺辉 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.ListUtils;
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
import com.google.common.collect.Maps;
import com.ydsh.generator.common.JsonResult;
import com.ydsh.goods.common.db.DBKeyGenerator;
import com.ydsh.goods.common.enums.DBBusinessKeyTypeEnums;
import com.ydsh.goods.common.enums.DBDictionaryEnumManager;
import com.ydsh.goods.common.enums.ErrorCode;
import com.ydsh.goods.common.exception.SystemException;
import com.ydsh.goods.common.util.TextUtils;
import com.ydsh.goods.web.controller.base.AbstractController;
import com.ydsh.goods.web.entity.GoodsCardSku;
import com.ydsh.goods.web.entity.GoodsPackage;
import com.ydsh.goods.web.entity.GoodsPublicPrice;
import com.ydsh.goods.web.entity.GoodsPublicPriceGoods;
import com.ydsh.goods.web.entity.dto.GoodsCardAndSkuDto;
import com.ydsh.goods.web.entity.dto.GoodsPublicPriceAndGoodsDto;
import com.ydsh.goods.web.entity.dto.GoodsPublicPriceGoodsDto;
import com.ydsh.goods.web.entity.dto.LookAndUpdateTakeInGoodsPublicPriceDto;
import com.ydsh.goods.web.entity.dto.RemoveGoodsPublicPriceDto;
import com.ydsh.goods.web.entity.dto.ReviewGoodsPublicPriceDto;
import com.ydsh.goods.web.service.GoodsCardService;
import com.ydsh.goods.web.service.GoodsCardSkuService;
import com.ydsh.goods.web.service.GoodsPackageService;
import com.ydsh.goods.web.service.GoodsPublicPriceGoodsService;
import com.ydsh.goods.web.service.GoodsPublicPriceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 自定义方法写在这里
 * </p>
 * 
 * <p>
 * 说明： 商品公共价管理表API接口层
 * </P>
 * 
 * @version: V1.0
 * @author: 戴艺辉
 *
 */
@Api(description = "商品公共价管理表", value = "商品公共价管理表")
@RestController
@RequestMapping("/goodsPublicPrice")
@Slf4j
public class GoodsPublicPriceController extends AbstractController<GoodsPublicPriceService, GoodsPublicPrice> {

	private static Logger logger = LoggerFactory.getLogger(GoodsCardController.class);

	private static Timestamp now = new Timestamp(System.currentTimeMillis());

	@Autowired
	private GoodsPublicPriceGoodsService goodsPublicPriceGoodsService;
	@Autowired
	private GoodsCardService goodsCardService;
	@Autowired
	private GoodsCardSkuService goodsCardSkuService;
	@Autowired
	private GoodsPackageService goodsPackageService;

	
	/**
	 * 
	 * 1-修改进入查看供应价调整单 
	 * 2-查看供应价调整单详情
	 * 
	 * @param @param  param
	 * @param @return
	 * @return
	 */
	@RequestMapping(value = "/lookGoodsPrice", method = RequestMethod.POST)
	@ApiOperation(value = "新增或修改供应价调整单", notes = "作者：戴艺辉")
	public JsonResult<Object> lookGoodsPrice(@RequestBody LookAndUpdateTakeInGoodsPublicPriceDto param) {
		String lookSign = param.getLookSign();
		JsonResult<Object> returnPage = new JsonResult<Object>();
		if (TextUtils.isEmpty(lookSign)) {
			logger.error("参数不能为空");
			throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空", new Exception());
		}
		switch (lookSign) {
		// 修改入口
		// 修改时 ：只能查看未审核的
		case "updateTakeIn": {
			Long id = param.getId();
			QueryWrapper<GoodsPublicPrice> queryWrapper = new QueryWrapper<GoodsPublicPrice>();
			queryWrapper.eq("id", id);
			Map<String, Object> goodsPublicPrice = baseService.getMap(queryWrapper);
			// 审核状态不为0，则报异常
			if (!((String) goodsPublicPrice.get("review_status")).equals(DBDictionaryEnumManager.review_0.getkey())) {
				logger.error("不是待审核状态，不允许修改！");
				returnPage.error("不是待审核状态，不允许修改！");
				return returnPage;
			}
			QueryWrapper<GoodsPublicPriceGoods> queryWrapperSecond = new QueryWrapper<GoodsPublicPriceGoods>();
			queryWrapper.eq("gpp_id", id);
			List<Map<String, Object>> goodsPublicPriceGoods = goodsPublicPriceGoodsService.listMaps(queryWrapperSecond);
			List<Map<String, Object>> listAdd = new LinkedList<Map<String, Object>>();

			// 如果是卡券商品
			String goodsType = (String) goodsPublicPrice.get("goods_type");
			if (goodsType.equals(DBDictionaryEnumManager.goods_card.getkey())) {
				for (Map<String, Object> var2 : goodsPublicPriceGoods) {
					String goodsId = String.valueOf(var2.get("goods_id"));
					GoodsCardAndSkuDto goodsCardAndSku = new GoodsCardAndSkuDto();
					goodsCardAndSku.setGcsId(Long.parseLong(goodsId));
					Map<String, Object> bossGoodsCardSku = goodsCardService.selectCardAndSKUPage(goodsCardAndSku);
					if (bossGoodsCardSku != null) {
						listAdd.add(bossGoodsCardSku);
					}
				}
				goodsPublicPrice.put("skuList", listAdd);
				returnPage.success("查询成功！");
				returnPage.setData(goodsPublicPrice);
				return returnPage;
			}
			// 如果是套餐商品
			else if (goodsType.equals(DBDictionaryEnumManager.package_card.getkey())) {
				for (Map<String, Object> var2 : goodsPublicPriceGoods) {
					String goodsId = String.valueOf(var2.get("goods_id"));
					QueryWrapper<GoodsPackage> GoodsPackageWrapper = new QueryWrapper<GoodsPackage>();
					queryWrapper.eq("id", goodsId);
					Map<String, Object> map = goodsPackageService.getMap(GoodsPackageWrapper);
					listAdd.add(map);
				}
				goodsPublicPrice.put("packageList", listAdd);
				returnPage.success("查询成功！");
				returnPage.setData(goodsPublicPrice);
				return returnPage;
			} else {
				logger.error("参数异常，");
				throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数异常", new Exception());
			}
		}
		// 查看
		case "lookSign": {
			Long id = param.getId();
			QueryWrapper<GoodsPublicPrice> queryWrapper = new QueryWrapper<GoodsPublicPrice>();
			queryWrapper.eq("id", id);
			Map<String, Object> goodsPublicPrice = baseService.getMap(queryWrapper);
			QueryWrapper<GoodsPublicPriceGoods> queryWrapperSecond = new QueryWrapper<GoodsPublicPriceGoods>();
			queryWrapper.eq("gpp_id", id);
			List<Map<String, Object>> goodsPublicPriceGoods = goodsPublicPriceGoodsService.listMaps(queryWrapperSecond);
			List<Map<String, Object>> listAdd = new LinkedList<Map<String, Object>>();

			// 如果是卡券商品
			String goodsType = (String) goodsPublicPrice.get("goods_type");
			if (goodsType.equals(DBDictionaryEnumManager.goods_card.getkey())) {
				for (Map<String, Object> var2 : goodsPublicPriceGoods) {
					String goodsId = String.valueOf(var2.get("goods_id"));
					GoodsCardAndSkuDto goodsCardAndSku = new GoodsCardAndSkuDto();
					goodsCardAndSku.setGcsId(Long.parseLong(goodsId));
					Map<String, Object> bossGoodsCardSku = goodsCardService.selectCardAndSKUPage(goodsCardAndSku);
					if (bossGoodsCardSku != null) {
						listAdd.add(bossGoodsCardSku);
					}
				}
				goodsPublicPrice.put("skuList", listAdd);
				returnPage.success("查询成功！");
				returnPage.setData(goodsPublicPrice);
				return returnPage;
			}
			// 如果是套餐商品
			else if (goodsType.equals(DBDictionaryEnumManager.package_card.getkey())) {
				for (Map<String, Object> var2 : goodsPublicPriceGoods) {
					String goodsId = String.valueOf(var2.get("goods_id"));
					QueryWrapper<GoodsPackage> GoodsPackageWrapper = new QueryWrapper<GoodsPackage>();
					queryWrapper.eq("id", goodsId);
					Map<String, Object> map = goodsPackageService.getMap(GoodsPackageWrapper);
					listAdd.add(map);
				}
				goodsPublicPrice.put("packageList", listAdd);
				returnPage.success("查询成功！");
				returnPage.setData(goodsPublicPrice);
				return returnPage;
			} else {
				logger.error("参数异常，");
				throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数异常", new Exception());
			}

		}
		default: {
			logger.error("参数异常，");
			throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数异常", new Exception());
		}
		}
	}

	/**
	 * 
	 * 1-新增供应价调整单 2-更新供应价调整单
	 * 
	 * @param @param  param
	 * @param @return
	 * @return
	 */
	@RequestMapping(value = "/addOrUpdateGoodsPricePage", method = RequestMethod.POST)
	@ApiOperation(value = "新增或修改供应价调整单", notes = "作者：戴艺辉")
	public JsonResult<Object> addOrUpdateGoodsPricePage(@RequestBody GoodsPublicPriceAndGoodsDto param) {
		String updateSign = param.getUpdateSign();
		JsonResult<Object> returnPage = new JsonResult<Object>();
		if (TextUtils.isEmpty(updateSign)) {
			logger.error("请求参数为空，");
			throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空", new Exception());
		}
		switch (updateSign) {
		// 新增
		case "add": {
			String priceStatus = param.getPriceStatus();
			String goodsType = param.getGoodsType();
			if (TextUtils.isEmptys(priceStatus, goodsType)) {
				logger.error("请求参数为空，");
				throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空", new Exception());
			}
			GoodsPublicPrice goodsPublicPrice = new GoodsPublicPrice();
			goodsPublicPrice.setGppNo(param.getGppNo());
			goodsPublicPrice.setPriceStatus(param.getPriceStatus());
			goodsPublicPrice.setGoodsType(param.getGoodsType());
			goodsPublicPrice.setSubmitTime(now);
			goodsPublicPrice.setReviewStatus(DBDictionaryEnumManager.review_0.getkey());
			String priceNo = DBKeyGenerator.generatorDBKey(DBBusinessKeyTypeEnums.PPA, null);
			goodsPublicPrice.setGppNo(priceNo);
			baseService.save(goodsPublicPrice);
			List<GoodsPublicPriceGoodsDto> skuList = param.getGoodsList();
			if (!(skuList == null || skuList.isEmpty())) {
				GoodsPublicPriceGoods goodsPublicPriceGoods = new GoodsPublicPriceGoods();
				for (GoodsPublicPriceGoodsDto var : skuList) {
					goodsPublicPriceGoods.setGppId(goodsPublicPrice.getId());
					goodsPublicPriceGoods.setGoodsId(var.getGoodsId());
					goodsPublicPriceGoods.setDefaultAmount((var.getDefaultAmount() == null ? 0
							: new BigDecimal(var.getDefaultAmount()).multiply(new BigDecimal("10000")).longValue()));
					goodsPublicPriceGoods.setNoticketAmount((var.getNoticketAmount() == null ? 0
							: new BigDecimal(var.getNoticketAmount()).multiply(new BigDecimal("10000")).longValue()));
					goodsPublicPriceGoods.setTicketSomeamount((var.getTicketSomeamount() == null ? 0
							: new BigDecimal(var.getTicketSomeamount()).multiply(new BigDecimal("10000")).longValue()));
					goodsPublicPriceGoods.setNoticketSomeamount((var.getNoticketSomeamount() == null ? 0
							: new BigDecimal(var.getNoticketSomeamount()).multiply(new BigDecimal("10000"))
									.longValue()));
					goodsPublicPriceGoods.setTicketAmount((var.getTicketAmount() == null ? 0
							: new BigDecimal(var.getTicketAmount()).multiply(new BigDecimal("10000")).longValue()));
					goodsPublicPriceGoodsService.save(goodsPublicPriceGoods);
				}
			}
			returnPage.success("新增成功，待审核完成生效！");
			return returnPage;
		}
		// 修改
		case "update": {
			// 价格调整单的id
			String id = String.valueOf(param.getId());
			String priceStatus = param.getPriceStatus();
			String goodsType = param.getGoodsType();
			if (TextUtils.isEmptys(id, priceStatus, goodsType)) {
				logger.error("请求参数为空，");
				throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空", new Exception());
			}
			GoodsPublicPrice goodsPublicPriceCheck = baseService.getById(id);
			if (goodsPublicPriceCheck == null) {
				logger.error("请求参数异常，");
				throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "请求参数异常", new Exception());
			}
			String reviewStatus = goodsPublicPriceCheck.getReviewStatus();
			if (!(reviewStatus.equals(DBDictionaryEnumManager.review_0.getkey())
					|| reviewStatus.equals(DBDictionaryEnumManager.review_2.getkey()))) {
				logger.error("审核状态不为待审核或审核不通过，不允许修改！");
				returnPage.error("审核状态不为待审核或审核不通过，不允许修改！");
				return returnPage;
			}
			GoodsPublicPrice goodsPublicPrice = new GoodsPublicPrice();
			goodsPublicPrice.setId(param.getId());
			goodsPublicPrice.setGppNo(param.getGppNo());
			goodsPublicPrice.setPriceStatus(param.getPriceStatus());
			goodsPublicPrice.setGoodsType(param.getGoodsType());
			goodsPublicPrice.setUpdateTime(now);
			goodsPublicPrice.setReviewStatus(DBDictionaryEnumManager.review_0.getkey());
			goodsPublicPrice.setGppNo(param.getGppNo());
			baseService.updateById(goodsPublicPrice);
			// 删除数据后在保存
			Map<String, Object> columnMap = new HashMap<String, Object>();
			columnMap.put("gpp_id", param.getId());
			goodsPublicPriceGoodsService.removeByMap(columnMap);

			List<GoodsPublicPriceGoodsDto> skuList = param.getGoodsList();
			if (!(skuList == null || skuList.isEmpty())) {
				GoodsPublicPriceGoods goodsPublicPriceGoods = new GoodsPublicPriceGoods();
				for (GoodsPublicPriceGoodsDto var : skuList) {
					goodsPublicPriceGoods.setGppId(goodsPublicPrice.getId());
					goodsPublicPriceGoods.setGoodsId(var.getGoodsId());
					goodsPublicPriceGoods.setDefaultAmount((var.getDefaultAmount() == null ? 0
							: new BigDecimal(var.getDefaultAmount()).multiply(new BigDecimal("10000")).longValue()));
					goodsPublicPriceGoods.setNoticketAmount((var.getNoticketAmount() == null ? 0
							: new BigDecimal(var.getNoticketAmount()).multiply(new BigDecimal("10000")).longValue()));
					goodsPublicPriceGoods.setTicketSomeamount((var.getTicketSomeamount() == null ? 0
							: new BigDecimal(var.getTicketSomeamount()).multiply(new BigDecimal("10000")).longValue()));
					goodsPublicPriceGoods.setNoticketSomeamount((var.getNoticketSomeamount() == null ? 0
							: new BigDecimal(var.getNoticketSomeamount()).multiply(new BigDecimal("10000"))
									.longValue()));
					goodsPublicPriceGoods.setTicketAmount((var.getTicketAmount() == null ? 0
							: new BigDecimal(var.getTicketAmount()).multiply(new BigDecimal("10000")).longValue()));
					goodsPublicPriceGoodsService.save(goodsPublicPriceGoods);
				}
			}
			returnPage.success("修改成功，待审核完成生效！");
			return returnPage;
		}
		default: {
			logger.error("参数异常，");
			throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数异常", new Exception());
		}
		}
	}

	/**
	 * 
	 * *删除供应价调整单
	 * 
	 * @param @param  param
	 * @param @return
	 * @return
	 */
	@RequestMapping(value = "/removeGoodsPricePage", method = RequestMethod.POST)
	@ApiOperation(value = "删除供应价调整单", notes = "作者：戴艺辉")
	public JsonResult<Object> removeGoodsPricePage(@RequestBody RemoveGoodsPublicPriceDto param) {
		JsonResult<Object> returnPage = new JsonResult<Object>();
		String id = String.valueOf(param.getId());
		if (TextUtils.isEmpty(id)) {
			logger.error("请求参数为空，");
			throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空", new Exception());
		}
		GoodsPublicPrice goodsPublicPriceCheck = baseService.getById(id);
		if (goodsPublicPriceCheck == null) {
			logger.error("参数异常，");
			throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数异常", new Exception());
		}
		String reviewStatus = goodsPublicPriceCheck.getReviewStatus();
		// 待审核和审核不通过的才允许删除
		if (reviewStatus.equals(DBDictionaryEnumManager.review_0.getkey())
				|| reviewStatus.equals(DBDictionaryEnumManager.review_2.getkey())) {
			baseService.removeById(id);
			Map<String, Object> columnMap = new HashMap<String, Object>();
			columnMap.put("gpp_id", id);
			goodsPublicPriceGoodsService.removeByMap(columnMap);
			returnPage.success("删除成功！");
			return returnPage;
		} else {
			returnPage.error("审核状态不为待审核或审核不通过的，不允许删除！");
			return returnPage;
		}
	}

	/**
	 * 
	 * *删除供应价调整单
	 * 
	 * @param @param  param
	 * @param @return
	 * @return
	 */
	@RequestMapping(value = "/reviewGoodsPricePage", method = RequestMethod.POST)
	@ApiOperation(value = "审核供应价调整单", notes = "作者：戴艺辉")
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public JsonResult<Object> reviewGoodsPricePage(@RequestBody ReviewGoodsPublicPriceDto param) {
		JsonResult<Object> returnPage = new JsonResult<Object>();
		String id = String.valueOf(param.getId());
		String auditorStatus = param.getReviewStatus();
		String auditorBz = param.getReviewRemarks();
		if (TextUtils.isEmptys(id, auditorStatus, auditorBz)) {
			logger.error("请求参数为空，");
			throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空", new Exception());
		}
		Timestamp now = new Timestamp(System.currentTimeMillis());
		GoodsPublicPrice goodsPublicPriceCheck = baseService.getById(id);
		if (goodsPublicPriceCheck == null) {
			logger.error("参数异常，");
			throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数异常", new Exception());
		}
		if (!(goodsPublicPriceCheck.getReviewStatus().equals(DBDictionaryEnumManager.review_0.getkey()))) {
			logger.error("不为待审核状态，不允许审核！");
			returnPage.error("不为待审核状态，不允许审核！");
			return returnPage;
		}
		GoodsPublicPrice bossGoodsPublicPrice = new GoodsPublicPrice();
		bossGoodsPublicPrice.setId(Long.parseLong(id));
		bossGoodsPublicPrice.setReviewStatus(auditorStatus);
		bossGoodsPublicPrice.setReviewRemarks(auditorBz);
		bossGoodsPublicPrice.setReviewTime(now);
		baseService.updateById(bossGoodsPublicPrice);

		// 审核通过
		if (auditorStatus.equals(DBDictionaryEnumManager.review_1.getkey())) {
			QueryWrapper<GoodsPublicPriceGoods> queryWrapper = new QueryWrapper<GoodsPublicPriceGoods>();
			queryWrapper.eq("gpp_id", id);
			List<GoodsPublicPriceGoods> priceWithGoodsList = goodsPublicPriceGoodsService.list(queryWrapper);
			for (GoodsPublicPriceGoods var2 : priceWithGoodsList) {
				// 如果是卡券商品
				if ((goodsPublicPriceCheck.getGoodsType()).equals(DBDictionaryEnumManager.goods_card.getkey())) {
					GoodsCardSku bossGoodsCardSku = new GoodsCardSku();
					bossGoodsCardSku.setId(var2.getGoodsId());
					bossGoodsCardSku.setDefaultAmount(var2.getDefaultAmount());
					bossGoodsCardSku.setNoticketAmount(var2.getNoticketAmount());
					bossGoodsCardSku.setTicketSomeamount(var2.getTicketSomeamount());
					bossGoodsCardSku.setNoticketSomeamount(var2.getNoticketSomeamount());
					bossGoodsCardSku.setTicketAmount(var2.getTicketAmount());
					goodsCardSkuService.updateById(bossGoodsCardSku);
				}
				// 如果是套餐商品
				else if ((goodsPublicPriceCheck.getGoodsType()).equals(DBDictionaryEnumManager.package_card.getkey())) {
					GoodsPackage bossGoodsPackage = new GoodsPackage();
					bossGoodsPackage.setId(var2.getGoodsId());
					bossGoodsPackage.setDefaultAmount(var2.getDefaultAmount());
					bossGoodsPackage.setNoticketAmount(var2.getNoticketAmount());
					bossGoodsPackage.setTicketSomeamount(var2.getTicketSomeamount());
					bossGoodsPackage.setNoticketSomeamount(var2.getNoticketSomeamount());
					bossGoodsPackage.setTicketAmount(var2.getTicketAmount());
					goodsPackageService.updateById(bossGoodsPackage);
				} else {
					logger.error("参数异常，");
					throw new SystemException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数异常", new Exception());
				}
			}
		}
		returnPage.success("审核成功！");
		return returnPage;
	}

}