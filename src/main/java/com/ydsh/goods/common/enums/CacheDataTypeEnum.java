package com.ydsh.goods.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 缓存数据类型类型
 *
 * @author: <a href="mailto:luojun@yidianlife.com">luojun</a>
 * @date: 2019年4月18日
 * @version v0.0.1
 */
public enum CacheDataTypeEnum {

	/** 基础配置信息缓存 */
	BASE_CONFIG("bc_", "基础配置信息"),
	/** BOSS用户Token */
	USER_TOKEN("ut_", "用户Token"),
	/** 分布式锁缓存 */
	PLAT_LOCK("lock_", "分布式锁"),
	/** 短信验证码 */
	CODE_SMS("cs_", "短信验证码"),
	/** 邮箱验证码 */
	CODE_MAIL("cm_", "邮箱验证码"),
	/** 数据库主键缓存 */
	DB_KEY("dk_", "数据库主键"),
	/** 系统配置信息 */
	SYS_CONF("sys_conf_", "系统配置信息"),
	//
	;

	private final String code;
	private final String desc;

	private CacheDataTypeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static CacheDataTypeEnum getEnumByCode(String resCode) {
		if (StringUtils.isEmpty(resCode)) {
			return null;
		}
		for (CacheDataTypeEnum each : values()) {
			if (each.getCode().equals(resCode)) {
				return each;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "[" + code + ":" + desc + "]";
	}
}
