package com.ydsh.goods.common.enums;


/**
 * 数据字典
 *
 * @author: <a href="mailto:daiyihui@yidianlife.com">daiyihui</a>
 * @date: 2019年5月16日
 * @version v0.0.1
 */
public enum DBDictionaryEnumManager {
	/**
	 * 1-充值状态
	 * 2-退款状态
	 * 3-审核状态
	 * 4-入库状态
	 * 5-出库状态
	 * 6-付款单状态
	 */
	review_0("r_0", "待审核"),
	review_1("r_1", "审核通过"),
	review_2("r_2", "审核不通过"),
	review_3("r_3", "待提交"),
	review_4("r_4", "已入库"),
	review_5("r_5", "已审核"),
	review_6("r_6", "已付款"),
	/**
	 * 1-是 
	 * 2-否
	 */
	yes("y","是"),
	no("n","否"),
	
	/**
	 * 账单明细
	 */
	bill_state_0("bs_0","未生成"),
	bill_state_1("bs_1","已生成"),
	bill_state_2("bs_2","异常"),
	
	/**
	 * 1-供应商账单状态 
	 * 2-客户账单状态
	 */
	bill_detail_0("bd_0","未结算"),
	bill_detail_1("bd_1","已结算"),
	bill_detail_2("bd_2","已付款"),
	bill_detail_3("bd_3","已扣款"),
	
	/**
	 * 商户状态 
	 */
	user_status_0("us_0","启用"),
	user_status_1("us_1","禁用"),
	user_status_2("us_2","黑名单"),
	/**
	 * 商品状态
	 */
	goods_0("g_0","上架"),
	goods_1("g_1","下架"),
	goods_2("g_2","作废"),
	/**
	 * 1-劵码状态
	 * 2-劵码验证状态
	 */
	coupon_code_0("cc_0","未出库"),
	coupon_code_1("cc_1","未验证"),
	coupon_code_2("cc_2","已验证"),
	coupon_code_3("cc_3","已过期"),
	coupon_code_4("cc_4","已冻结"),
	coupon_code_5("cc_5","已作废"),
	
	/**
	 * 删除标志
	 */
	invalid("0","无效"),
	valid("1","有效"),
	
	/**
	 * *商品有效期类型
	 */
	fixed_valid_day("fvd","固定有效期 "),
	valid_day("vd","有效天数 "),
	
	/**
	 * *商品类型
	 */
	goods_card("gc","卡券商品"),
	package_card("pc","套餐商品"),

	/**
	 * *充值账本
	 */
	account_book_0("ab_0","余额充值"),
	account_book_1("ab_1","佣金充值"),
	account_book_2("ab_2","预付充值"),
	account_book_3("ab_3","返利充值"),
	account_book_4("ab_4","回款充值"),
	
	/**
	 * 采购状态
	 */
	purchase_status_0("ps_0","待采购"),
	purchase_status_1("ps_1","暂不采购"),
	purchase_status_2("ps_2","无需采购"),
	purchase_status_3("ps_3","待处理"),
	
	/**
	 * *结算方式：入库结算、核销结算、发放结算、订单结算
	 */
	settlement_method_0("sm_0","入库结算"),
	settlement_method_1("sm_1","核销结算"),
	settlement_method_2("sm_2","发放结算"),
	settlement_method_3("sm_3","订单结算"),
	
	/**
	 * 延期类型
	 */
	delay_type_1("dt_1","原有效结束时间延期"),
	delay_type_2("dt_2","原虚拟结束时间延期"),
	
	/**
	 * 劵码操作审核
	 */
	coupon_code_review_1("ccr_1","作废"),
	coupon_code_review_2("ccr_2","延期"),
	coupon_code_review_3("ccr_3","冻结"),
	coupon_code_review_4("ccr_4","加密导出"),
	
	
	
	
	;

	/** 枚举值 */
	private final String key;

	/** 枚举描述 */
	private final String message;

	/**
	 * 构造方法
	 * 
	 * @param key
	 * @param message
	 */
	DBDictionaryEnumManager(String key, String message) {
		this.key = key;
		this.message = message;
	}

	public String getkey() {
		return key;
	}

	public String getMessage() {
		return message;
	}

	/**
	 * 通过key获取msg
	 *
	 * @param key 枚举值
	 * @return
	 */
	public static String getMsgBykey(String key) {
		if (key == null || "".equals(key)) {
			return null;
		}
		DBDictionaryEnumManager enumList = getBykey(key);
		if (enumList == null) {
			return null;
		}
		return enumList.getMessage();
	}

	/** 
	 * 通过枚举<key>key</key>获得枚举
	 * values() 方法将枚举转变为数组
	 *
	 * @return AuthGradeEnum
	 */
	public static DBDictionaryEnumManager getBykey(String key) {
		for (DBDictionaryEnumManager enumList : values()) {
			if (enumList.getkey().equals(key)) {
				return enumList;
			}
		}
		return null;
	}
}
