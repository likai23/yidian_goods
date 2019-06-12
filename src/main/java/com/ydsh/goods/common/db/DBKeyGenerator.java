package com.ydsh.goods.common.db;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.ydsh.goods.common.enums.CacheDataTypeEnum;
import com.ydsh.goods.common.enums.DBBusinessKeyTypeEnums;

/**
 * <p>
 * 生成业务编码主键
 * </p>
 *
 * @author <a href="mailto:yangyanrui@yidianlife.com">xiaoyang</a>
 * @version V0.0.1
 * @date 2019年05月17日
 */
@Component
public class DBKeyGenerator {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public static StringRedisTemplate redisClient;
    @PostConstruct
    public void init(){
        redisClient =this.stringRedisTemplate;
    }
    /**
     * 生成业务主键
     *
     * @param dbBusinessKeyTypeEnums 类型
     * @param noKey                  额外标识
     * @return
     */
    public static String generatorDBKey(DBBusinessKeyTypeEnums dbBusinessKeyTypeEnums, String noKey) {
        Long num;
        switch (dbBusinessKeyTypeEnums) {
            case SKU:
                num = redisClient.opsForValue().increment(CacheDataTypeEnum.DB_KEY.getCode() + noKey);
                if (num >= dbBusinessKeyTypeEnums.getMaxNum()) {
                    num = redisClient.opsForValue().decrement(CacheDataTypeEnum.DB_KEY.getCode() + noKey, dbBusinessKeyTypeEnums.getMinusMun());
                }
                break;
            default:
                num = redisClient.opsForValue().increment(CacheDataTypeEnum.DB_KEY.getCode() + dbBusinessKeyTypeEnums.name());
                if (num >= dbBusinessKeyTypeEnums.getMaxNum()) {
                    num = redisClient.opsForValue().decrement(CacheDataTypeEnum.DB_KEY.getCode() + dbBusinessKeyTypeEnums.name(), dbBusinessKeyTypeEnums.getMinusMun());
                }
        }
        return generatorDBKey(dbBusinessKeyTypeEnums, noKey, DateTimeFormatter.ofPattern("yyyMMdd").format(LocalDate.now()).toString(), String.format(dbBusinessKeyTypeEnums.getFormula(), num));
    }

    /**
     * 组装业务唯一进制
     *
     * @param dbBusinessKeyTypeEnums 类型
     * @param noKey                  额外标识
     * @param date                   时间
     * @param num                    自增数字
     * @return
     */
    private static String generatorDBKey(DBBusinessKeyTypeEnums dbBusinessKeyTypeEnums, String noKey, String date, String num) {
        String no = null;
        String noTypeName = "";
        switch (dbBusinessKeyTypeEnums) {
            case SKU:
                noTypeName = noKey;
                break;
            default:
                noTypeName = dbBusinessKeyTypeEnums.name();
                if (!StringUtils.isEmpty(noKey)) {
                    noTypeName += noKey;
                }
        }
        if (dbBusinessKeyTypeEnums.isOrNotTime()) {
            no = noTypeName + date + num;
        } else {
            no = noTypeName + num;
        }
        return no;
    }
}
