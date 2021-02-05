package com.cdqckj.gmis.common.domain.code.next;

import com.cdqckj.gmis.common.domain.code.CodeInfo;
import com.cdqckj.gmis.common.domain.code.CodeTypeEnum;
import com.cdqckj.gmis.lock.DistributedLock;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author: lijianguo
 * @time: 2020/12/23 09:32
 * @remark: 生成nextCode的工具类
 */
@Log4j2
@Component
public class NextCodeFactoryUtil {

    @Autowired
    RedisTemplate redisTemplateInit;

    @Autowired
    private DistributedLock distributedLockInit;

    /** 缓存 **/
    private static RedisTemplate redisTemplate;

    /** 分布式锁 **/
    private static DistributedLock redisDistributedLock;

    @PostConstruct
    public void initStaticData() {
        NextCodeFactoryUtil.redisTemplate = redisTemplateInit;
        NextCodeFactoryUtil.redisDistributedLock = distributedLockInit;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/23 15:18
    * @remark 生成next code
    */
    public static NextCode createNextCode(CodeInfo codeInfo, String codePrefix, Integer length){
        if (StringUtils.isAnyBlank(codeInfo.getTableName(), codeInfo.getColName())){
            log.error("参数不能为空 {} {}", codeInfo.getTableName(), codeInfo.getColName());
            throw new RuntimeException("参数不能为空");
        }
        if (length == null){
            throw new RuntimeException("必须指定编码长度");
        }
        if (codeInfo.getCodeTypeEnum() == CodeTypeEnum.INC_TYPE_ONE){
            return new NextCodeInc(codeInfo, length, codePrefix , redisTemplate);
        }else if (codeInfo.getCodeTypeEnum() == CodeTypeEnum.RANDOM_TYPE_ONE){
            return new NextCodeString(length, codePrefix);
        }else {
            throw new RuntimeException("编码类型不存在");
        }
    }

}
