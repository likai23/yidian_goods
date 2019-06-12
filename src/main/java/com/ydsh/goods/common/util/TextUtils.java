package com.ydsh.goods.common.util;

import java.util.Map;

/**
 * 
* <p>
* 通用工具类
* </p>
* @author <a href="mailto:daiyihui@yidianlife.com">daiyihui</a>
* @version V0.0.1
* @date 2019年6月5日
 */
public class TextUtils {

	/**
	 * 
	*getMapForKeyToString
	*
	* @param map
	* @param key
	* @return
	 */
	public static String getMapForKeyToString(Map<String,Object> map,String key) {
		Object keyValue = map.get(key);
		if(keyValue==null||keyValue.toString().length()==0) {
			return null;
		}else {
			return keyValue.toString();
		}
	}
	/**
	 * 
	* getMapForKeyToString
	*
	* @param map
	* @param key
	* @return
	 */
	public static boolean isEmpty(String key) {
		if("".equals(key)||key.length()==0) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 
	*多参判空
	*
	* @param Object
	* @return
	 */
	public static boolean isEmptys(String... objs) {
		boolean mark = false;
		for (String obj : objs) {
			if (isEmpty(obj)) {
				mark = true;
			}
		}
		return mark;
	}
}
