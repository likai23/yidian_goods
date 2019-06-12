package com.ydsh.goods.common.enums;

/**
 * 数据库业务主键类型、规则 枚举
 *
 * @author: <a href="mailto:xxx@yidianlife.com">xxx</a>
 * @date: 2019年5月8日
 * @version v0.0.1
 */
public enum DBBusinessKeyTypeEnums {

	/** 供应商编号：supplier S+2位行业类别编码+四位数字递增 C010001 */
	S(10000, 9999l,"%04d", false),
	/** 客户编号：customer C+2位行业类别编码+四位数字递增 C010001 */
	C(10000, 9999l, "%04d", false),
	/**采购申请单号：Purchasing application PA +年份日期+两位数字递增 PAS2018020201**/
	PA(100, 99l, "%02d", true),
	/**采购单号：procurement P+年份日期+四位数字递增 P201812250001**/
	P(10000, 9999l, "%04d", true),
	/**付款申请订单：Payment request PR+年份日期+两位数字递增 PRS2018020201**/
	PR(100, 99l, "%02d", true),
	/**采购价调整单号：Purchasing price adjustment PPA+年份日期+两位数字递增 PPA2018020201**/
	PPA(100, 99l, "%02d", true),
	/**合作价调整单号：Cooperation price adjustment CPA+年份日期+两位数字递增 CPA2018020201**/
	CPA(100, 99l, "%02d", true),
	/** 商品公共价调整单号： Goods price adjustment GPA+四位数字递增 GPA0001**/
	GPA(10000, 9999l, "%04d", true),
	/**入库单号：in storage IS+年份日期+八位数字递增 IS2018122500000001**/
	IS(100000000, 99999999l, "%08d", true),
	/**出库单号：out storage OS+年份日期+八位数字递增 OS2018122500000001**/
	OS(100000000, 99999999l, "%08d", true),
	/**卡券商品编码：Goods G+10+3位类目编码+四位数字递增 G100010001**/
	G10(10000, 9999l, "%04d", false),
	/**实体卡商品编码： Goods G+20+3位类目编码+四位数字递增 G200010001**/
	G20(10000, 9999l, "%04d", false),
	/**直充商品编码：G+30+3位类别编码+四位数字递增 G300010001**/
	G30(10000, 9999l, "%04d", false),
	/** 实物商品编码：Goods G+90+3位类目编码+四位数字递增 G900010001**/
	G90(10000, 9999l, "%04d", false),
	/**套餐编号：combo CP+六位数字递增 C000001**/
	CP(1000000, 999999l, "%06d", false),
	/**券码编号：Coupon code CC+年份日期+八位数字递增 CC2018122500000001**/
	CC(100000000, 99999999l, "%08d", true),
	/**作废审核：Invalid application IA +年份日期+四位数字递增 IA 201812250001**/
	IA(10000, 9999l, "%04d", true),
	/**延期审核：Delay application DA+年份日期+四位数字递增 DA 201812250001**/
	DA(10000, 9999l, "%04d", true),
	/**冻结审核：Freeze application FA+年份日期+四位数字递增 FA 201812250001**/
	FA(10000, 9999l, "%04d", true),
	/**加密导出审核：export application EA+年份日期+四位数字递增 EA 201812250001**/
	EA(10000, 9999l, "%04d", true),
	/**兑换码编号：redeem code RC+年份日期+六位数字递增 RC20181225000001**/
	RC(1000000, 999999l, "%06d", true),
	/**卡券活动编号 activity A+年份日期+四位数字递增 A201812250001**/
	A(10000, 9999l, "%04d", true),
	/**直充兑换码活动编号：RAT+年份日期+四位数字递增 RAT2201812250001**/
	RAT(10000, 9999l, "%04d", true),
	/**卡券订单编号：order code OC+年份日期+六位数字递增 OC20181225000001**/
	OC(1000000, 999999l, "%06d", true),
	/**直充订单编号：order top-up OT+年份日期+六位数字递增 OT20181225000001**/
	OT(1000000, 999999l, "%06d", true),
	/**直充兑换码订单编号：redeem order top-up ROT+年份日期+六位数字递增 ROT20181225000001**/
	ROT(1000000, 999999l, "%06d", true),
	/**售后订单号：After-sale Oder AO+年份日期+两位数字递增 AO2018020201**/
	AO(100, 99l, "%02d", true),
	/**支付流水单号：payment PM+年份日期+八位数字递增 PM2018122500000001**/
	PM(100000000, 99999999l, "%08d", true),
	/**供应商结算账单编号：supplier bill SB+生成日期+两位数字递增 SB2018122501**/
	SB(100, 99l, "%02d", true),
	/**客户结算账单编号：customer bill CB+生成日期+两位数字递增 CB2018122501**/
	CB(100, 99l, "%02d", true),
	/**卡券商品SKU编码、直充商品SKU编码、实体卡商品SKU编码、实物商品SKU编码**/
	SKU(100, 99l, "%02d", false),
	/**卡券商品SKU编码、直充商品SKU编码、实体卡商品SKU编码、实物商品SKU编码**/
	AI(1000000, 999999l, "%06d", false),
	;

	private final int maxNum;
	private final long minusMun;
	private final String formula;
	private final boolean orNotTime;

	/**
	 * 规则
	 *
	 * @param maxNum 递增最大值
	 * @param minusMun 扣除最大值
	 * @param formula 数字位数 不足位前面补0
	 * @param orNotTime 是否加入时间
	 */
	DBBusinessKeyTypeEnums(int maxNum, long minusMun, String formula, boolean orNotTime) {
		this.maxNum = maxNum;
		this.minusMun = minusMun;
		this.formula = formula;
		this.orNotTime = orNotTime;
	}

	public int getMaxNum() {
		return maxNum;
	}

	public long getMinusMun() {
		return minusMun;
	}

	public String getFormula() {
		return formula;
	}

	public boolean isOrNotTime() {
		return orNotTime;
	}
}
