package com.cdqckj.gmis.common.domain.code.next;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.cdqckj.gmis.common.domain.code.CodeInfo;
import com.cdqckj.gmis.common.domain.code.DataBaseUtil;
import com.cdqckj.gmis.common.domain.code.clear.ClearNowException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: lijianguo
 * @time: 2020/12/07 13:05
 * @remark: 自增的code
 */
public class NextCodeInc implements NextCode {

    /** 长度 **/
    private Integer codeLength;

    /** 查询的前缀 **/
    private String prefix;

    /** 缓存 **/
    private RedisTemplate redisTemplate;

    /** 保存的信息 **/
    private CodeInfo codeInfo;

    public NextCodeInc(CodeInfo codeInfo, Integer codeLength, String prefix, RedisTemplate redisTemplate) {
        this.codeInfo = codeInfo;
        this.codeLength = codeLength;
        this.prefix = prefix;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String create() {

        String incKey = codeInfo.nextIncKey();
        if (!redisTemplate.hasKey(incKey)){
            Long num = getNextMaxNum();
            redisTemplate.opsForValue().set(incKey, num);
        }
        Long next = redisTemplate.opsForValue().increment(incKey, 1);
        redisTemplate.expire(incKey, 10, TimeUnit.DAYS);
        if (exceedMaxValue(next)){
            redisTemplate.opsForValue().increment(incKey, -1);
            throw new ClearNowException("自增编码超出最大的限制");
        }

        String str = String.format("%0"+codeLength+"d",next);
        return str;
    }

    @Override
    public String codePrefix() {
        return prefix;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/24 10:07
    * @remark 获取查询的数据
    */
    private String getSearchSql(){
        return codeInfo.nextCodeSql();
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/24 10:10
    * @remark 获取最大的数字
    */
    private Long getNextMaxNum(){

        List<String> dataList = DataBaseUtil.getOneColData(getSearchSql(), codeInfo.getColName());
        Long max = 0L;
        for (String code: dataList){

            if (StringUtils.isBlank(code)){
                continue;
            }
            String newCode = new String();
            for(int i = code.length() - 1; i >= 0; i--){
                if(code.charAt(i) >= 48 && code.charAt(i) <= 57) {
                    newCode += code.charAt(i);
                }else {
                    break;
                }
            }
            if (StringUtils.isBlank(newCode)){
                continue;
            }
            if (newCode.length() > codeLength){
                newCode = newCode.substring(0, codeLength);
            }
            newCode = StrUtil.reverse(newCode);
            Long num = NumberUtil.parseLong(newCode);
            max = max > num ? max: num;
        }
        return max;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/24 11:26
    * @remark 超出自增长的最大的限制
    */
    private Boolean exceedMaxValue(Long value){

        Long maxValue = 1L;
        for (int i = 0; i < codeLength; i++){
            maxValue *= 10;
        }
        maxValue *= 10;
        if (value >= maxValue){
            return true;
        }else {
            return false;
        }
    }
}
