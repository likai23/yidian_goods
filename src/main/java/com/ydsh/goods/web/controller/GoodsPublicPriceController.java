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

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ydsh.generator.common.JsonResult;
import com.ydsh.goods.common.db.DBKeyGenerator;
import com.ydsh.goods.common.enums.DBBusinessKeyTypeEnums;
import com.ydsh.goods.common.enums.DBDictionaryEnumManager;
import com.ydsh.goods.common.enums.ErrorCode;
import com.ydsh.goods.common.exception.BizException;
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
import com.ydsh.goods.web.entity.dto.LookRetrunGoodsPublicPriceDto;
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
	 *  *查看供应价调整单详情
	 * 
	 * @param @param  param
	 * @param @return
	 * @return
	 */
	@RequestMapping(value = "/lookGoodsPrice", method = RequestMethod.POST)
	@ApiOperation(value = "查看供应价调整单详情", notes = "作者：戴艺辉")
	public JsonResult<LookRetrunGoodsPublicPriceDto> lookGoodsPrice(@RequestBody LookAndUpdateTakeInGoodsPublicPriceDto param) {
		JsonResult<LookRetrunGoodsPublicPriceDto> returnPage = new JsonResult<LookRetrunGoodsPublicPriceDto>();
		// 查看
		Long id = param.getId();
		if(id==null) {
			log.error("请求参数为空，");
			throw new BizException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空");
		}
		QueryWrapper<GoodsPublicPrice> queryWrapper = new QueryWrapper<GoodsPublicPrice>();
		queryWrapper.eq("id", id);
		GoodsPublicPrice goodsPublicPrice = baseService.getById(id);
		LookRetrunGoodsPublicPriceDto goodsPublicPriceDto=new LookRetrunGoodsPublicPriceDto();
		BeanUtils.copyProperties(goodsPublicPrice, goodsPublicPriceDto);
		QueryWrapper<GoodsPublicPriceGoods> queryWrapperSecond = new QueryWrapper<GoodsPublicPriceGoods>();
		queryWrapper.eq("gpp_id", id);
		List<Map<String, Object>> goodsPublicPriceGoods = goodsPublicPriceGoodsService.listMaps(queryWrapperSecond);
		// 如果是卡券商品
		String goodsType = (String) goodsPublicPrice.getGoodsType();
		if (goodsType.equals(DBDictionaryEnumManager.goods_card.getkey())) {
			List<GoodsCardAndSkuDto> listAdd = new LinkedList<GoodsCardAndSkuDto>();
			for (Map<String, Object> var2 : goodsPublicPriceGoods) {
				String goodsId = String.valueOf(var2.get("goods_id"));
				GoodsCardAndSkuDto goodsCardAndSku = new GoodsCardAndSkuDto();
				goodsCardAndSku.setGcsId(Long.parseLong(goodsId));
				GoodsCardAndSkuDto bossGoodsCardSku = goodsCardService.selectCardAndSKUPage(goodsCardAndSku);
				if (bossGoodsCardSku != null) {
					listAdd.add(bossGoodsCardSku);
				}
			}
			goodsPublicPriceDto.setSkuList(listAdd);
			returnPage.success("查询成功！");
			returnPage.setData(goodsPublicPriceDto);
			return returnPage;
		}
		// 如果是套餐商品
		else if (goodsType.equals(DBDictionaryEnumManager.package_card.getkey())) {
			List<GoodsPackage> listAdd = new LinkedList<GoodsPackage>();
			for (Map<String, Object> var2 : goodsPublicPriceGoods) {
				String goodsId = String.valueOf(var2.get("goods_id"));
				GoodsPackage map = goodsPackageService.getById(goodsId);
				listAdd.add(map);
			}
			goodsPublicPriceDto.setPackageList(listAdd);
			returnPage.success("查询成功！");
			returnPage.setData(goodsPublicPriceDto);
			return returnPage;
		} else {
			log.error("参数异常，");
			throw new BizException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数异常");
		}
	}

	/**
	 * 
	 * *修改进入查看供应价调整单
	 * 
	 * @param @param  param
	 * @param @return
	 * @return
	 */
	@RequestMapping(value = "/updateTakeInGoodsPrice", method = RequestMethod.POST)
	@ApiOperation(value = "修改进入查看供应价调整单", notes = "作者：戴艺辉")
	public JsonResult<LookRetrunGoodsPublicPriceDto> updateTakeInGoodsPrice(@RequestBody LookAndUpdateTakeInGoodsPublicPriceDto param) {
		JsonResult<LookRetrunGoodsPublicPriceDto> returnPage = new JsonResult<LookRetrunGoodsPublicPriceDto>();
		Long id = param.getId();
		if(TextUtils.isEmpty(String.valueOf(id))) {
			log.error("请求参数为空，");
			throw new BizException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空");
		}
		QueryWrapper<GoodsPublicPrice> queryWrapper = new QueryWrapper<GoodsPublicPrice>();
		queryWrapper.eq("id", id);
		GoodsPublicPrice goodsPublicPrice = baseService.getById(id);
		// 审核状态不为0，则报异常
		if (!(goodsPublicPrice.getReviewStatus()).equals(DBDictionaryEnumManager.review_0.getkey())) {
			log.error("不是待审核状态，不允许修改！");
			returnPage.error("不是待审核状态，不允许修改！");
			return returnPage;
		}
		LookRetrunGoodsPublicPriceDto goodsPublicPriceDto=new LookRetrunGoodsPublicPriceDto();
		BeanUtils.copyProperties(goodsPublicPrice, goodsPublicPriceDto);
		QueryWrapper<GoodsPublicPriceGoods> queryWrapperSecond = new QueryWrapper<GoodsPublicPriceGoods>();
		queryWrapper.eq("gpp_id", id);
		List<Map<String, Object>> goodsPublicPriceGoods = goodsPublicPriceGoodsService.listMaps(queryWrapperSecond);
		// 如果是卡券商品
		String goodsType = (String) goodsPublicPrice.getGoodsType();
		if (goodsType.equals(DBDictionaryEnumManager.goods_card.getkey())) {
			List<GoodsCardAndSkuDto> listAdd = new LinkedList<GoodsCardAndSkuDto>();
			for (Map<String, Object> var2 : goodsPublicPriceGoods) {
				String goodsId = String.valueOf(var2.get("goods_id"));
				GoodsCardAndSkuDto goodsCardAndSku = new GoodsCardAndSkuDto();
				goodsCardAndSku.setGcsId(Long.parseLong(goodsId));
				GoodsCardAndSkuDto bossGoodsCardSku = goodsCardService.selectCardAndSKUPage(goodsCardAndSku);
				if (bossGoodsCardSku != null) {
					listAdd.add(bossGoodsCardSku);
				}
			}
			goodsPublicPriceDto.setSkuList(listAdd);
			returnPage.success("查询成功！");
			returnPage.setData(goodsPublicPriceDto);
			return returnPage;
		}
		// 如果是套餐商品
		else if (goodsType.equals(DBDictionaryEnumManager.package_card.getkey())) {
			List<GoodsPackage> listAdd = new LinkedList<GoodsPackage>();
			for (Map<String, Object> var2 : goodsPublicPriceGoods) {
				String goodsId = String.valueOf(var2.get("goods_id"));
				GoodsPackage map = goodsPackageService.getById(goodsId);
				listAdd.add(map);
			}
			goodsPublicPriceDto.setPackageList(listAdd);
			returnPage.success("查询成功！");
			returnPage.setData(goodsPublicPriceDto);
			return returnPage;
		} else {
			log.error("参数异常，");
			throw new BizException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数异常");
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
			log.error("请求参数为空，");
			throw new BizException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空");
		}
		switch (updateSign) {
		// 新增
		case "add": {
			String priceStatus = param.getPriceStatus();
			String goodsType = param.getGoodsType();
			if (TextUtils.isEmptys(priceStatus, goodsType)) {
				log.error("请求参数为空，");
				throw new BizException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空");
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
				log.error("请求参数为空，");
				throw new BizException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空");
			}
			GoodsPublicPrice goodsPublicPriceCheck = baseService.getById(id);
			if (goodsPublicPriceCheck == null) {
				log.error("请求参数异常，");
				throw new BizException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "请求参数异常");
			}
			String reviewStatus = goodsPublicPriceCheck.getReviewStatus();
			if (!(reviewStatus.equals(DBDictionaryEnumManager.review_0.getkey())
					|| reviewStatus.equals(DBDictionaryEnumManager.review_2.getkey()))) {
				log.error("审核状态不为待审核或审核不通过，不允许修改！");
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
			log.error("参数异常，");
			throw new BizException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数异常");
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
	@RequestMapping(value = "/removeGoodsPrice", method = RequestMethod.POST)
	@ApiOperation(value = "删除供应价调整单", notes = "作者：戴艺辉")
	public JsonResult<Object> removeGoodsPrice(@RequestBody RemoveGoodsPublicPriceDto param) {
		JsonResult<Object> returnPage = new JsonResult<Object>();
		String id = String.valueOf(param.getId());
		if (TextUtils.isEmpty(id)) {
			log.error("请求参数为空，");
			throw new BizException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空");
		}
		GoodsPublicPrice goodsPublicPriceCheck = baseService.getById(id);
		if (goodsPublicPriceCheck == null) {
			log.error("参数异常，");
			throw new BizException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数异常");
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
	 * *审核供应价调整单
	 * 
	 * @param @param  param
	 * @param @return
	 * @return
	 */
	@RequestMapping(value = "/reviewGoodsPrice", method = RequestMethod.POST)
	@ApiOperation(value = "审核供应价调整单", notes = "作者：戴艺辉")
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public JsonResult<Object> reviewGoodsPrice(@RequestBody ReviewGoodsPublicPriceDto param) {
		JsonResult<Object> returnPage = new JsonResult<Object>();
		String id = String.valueOf(param.getId());
		String auditorStatus = param.getReviewStatus();
		String auditorBz = param.getReviewRemarks();
		if (TextUtils.isEmptys(id, auditorStatus, auditorBz)) {
			log.error("请求参数为空，");
			throw new BizException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数不能为空");
		}
		Timestamp now = new Timestamp(System.currentTimeMillis());
		GoodsPublicPrice goodsPublicPriceCheck = baseService.getById(id);
		if (goodsPublicPriceCheck == null) {
			log.error("参数异常，");
			throw new BizException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数异常");
		}
		if (!(goodsPublicPriceCheck.getReviewStatus().equals(DBDictionaryEnumManager.review_0.getkey()))) {
			log.error("不为待审核状态，不允许审核！");
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
					bossGoodsCardSku.setDefaultAmount(
							new BigDecimal(var2.getDefaultAmount()).multiply(new BigDecimal("0.0001")).longValue());
					bossGoodsCardSku.setNoticketAmount(
							new BigDecimal(var2.getNoticketAmount()).multiply(new BigDecimal("0.0001")).longValue());
					bossGoodsCardSku.setTicketSomeamount(
							new BigDecimal(var2.getTicketSomeamount()).multiply(new BigDecimal("0.0001")).longValue());
					bossGoodsCardSku.setNoticketSomeamount(new BigDecimal(var2.getNoticketSomeamount())
							.multiply(new BigDecimal("0.0001")).longValue());
					bossGoodsCardSku.setTicketAmount(
							new BigDecimal(var2.getTicketAmount()).multiply(new BigDecimal("0.0001")).longValue());
					goodsCardSkuService.updateById(bossGoodsCardSku);
				}
				// 如果是套餐商品
				else if ((goodsPublicPriceCheck.getGoodsType()).equals(DBDictionaryEnumManager.package_card.getkey())) {
					GoodsPackage bossGoodsPackage = new GoodsPackage();
					bossGoodsPackage.setId(var2.getGoodsId());
					bossGoodsPackage.setDefaultAmount(
							new BigDecimal(var2.getDefaultAmount()).multiply(new BigDecimal("0.0001")).longValue());
					bossGoodsPackage.setNoticketAmount(
							new BigDecimal(var2.getNoticketAmount()).multiply(new BigDecimal("0.0001")).longValue());
					bossGoodsPackage.setTicketSomeamount(
							new BigDecimal(var2.getTicketSomeamount()).multiply(new BigDecimal("0.0001")).longValue());
					bossGoodsPackage.setNoticketSomeamount(new BigDecimal(var2.getNoticketSomeamount())
							.multiply(new BigDecimal("0.0001")).longValue());
					bossGoodsPackage.setTicketAmount(
							new BigDecimal(var2.getTicketAmount()).multiply(new BigDecimal("0.0001")).longValue());
					goodsPackageService.updateById(bossGoodsPackage);
				} else {
					log.error("参数异常，");
					throw new BizException(ErrorCode.ILLEGAL_ARGUMENT.getCode(), "参数异常");
				}
			}
		}
		returnPage.success("审核成功！");
		return returnPage;
	}

}