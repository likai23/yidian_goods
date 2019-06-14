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
	review_0("0", "待审核"),
	review_1("1", "审核通过"),
	review_2("2", "审核不通过"),
	review_3("3", "待提交"),
	review_4("4", "已入库"),
	review_5("5", "已审核"),
	review_6("6", "已付款"),
	/**
	 * 1-是 
	 * 2-否
	 */
	yes("0","是"),
	no("1","否"),
	
	/**
	 * 账单明细
	 */
	bill_state_0("0","未生成"),
	bill_state_1("1","已生成"),
	bill_state_2("2","异常"),
	
	/**
	 * 1-供应商账单状态 
	 * 2-客户账单状态
	 */
	bill_detail_0("0","未结算"),
	bill_detail_1("1","已结算"),
	bill_detail_2("2","已付款"),
	bill_detail_3("3","已扣款"),
	
	/**
	 * 启用状态 
	 */
	user_status_0("0","启用"),
	user_status_1("1","禁用"),
	user_status_2("2","黑名单"),
	/**
	 * 商品状态
	 */
	goods_0("0","上架"),
	goods_1("1","下架"),
	goods_2("2","作废"),
	/**
	 * 1-劵码状态
	 * 2-劵码验证状态
	 */
	coupon_code_0("0","未出库"),
	coupon_code_1("1","未验证"),
	coupon_code_2("2","已验证"),
	coupon_code_3("3","已过期"),
	coupon_code_4("4","已冻结"),
	coupon_code_5("5","已作废"),
	
	/**
	 * 删除标志
	 */
	invalid("0","无效"),
	valid("1","有效"),
	
	/**
	 *商品有效期类型
	 */
	fixed_valid_day("0","固定有效期 "),
	valid_day("1","有效天数 "),
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
	private static DBDictionaryEnumManager getBykey(String key) {
		for (DBDictionaryEnumManager enumList : values()) {
			if (enumList.getkey().equals(key)) {
				return enumList;
			}
		}
		return null;
	}
}
