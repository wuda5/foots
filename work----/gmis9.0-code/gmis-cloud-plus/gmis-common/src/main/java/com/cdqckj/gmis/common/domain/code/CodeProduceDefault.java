package com.cdqckj.gmis.common.domain.code;

import com.cdqckj.gmis.common.domain.code.next.NextCode;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.lock.DistributedLock;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: lijianguo
 * @time: 2020/12/07 14:29
 * @remark: 请添加类说明
 */
@Log4j2
public class CodeProduceDefault implements CodeProduce {

    /** 缓存 **/
    protected RedisTemplate redisTemplate;

    /** 分布式锁 **/
    private  DistributedLock redisDistributedLock;

    /** 随机的重试次数最多次数 **/
    private final Integer MAX_RETRY_COUNT = 10000;

    public CodeProduceDefault(RedisTemplate redisTemplate, DistributedLock redisDistributedLock) {
        this.redisTemplate = redisTemplate;
        this.redisDistributedLock = redisDistributedLock;
    }

    @Override
    public String getCode(CodeInfo codeInfo, NextCode nextCode) {
        String cacheKey = codeInfo.codeCacheKey();
        if (!redisTemplate.hasKey(cacheKey)){
            cacheInitData(codeInfo);
        }
        String code = getNextCode(codeInfo, nextCode, MAX_RETRY_COUNT);
        return code;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/7 14:46
    * @remark 缓存初始化数据-这个时候从数据库获取数据
    */
    protected void cacheInitData(CodeInfo codeInfo){

        Boolean lockState = redisDistributedLock.lock(codeInfo.lockKey());
        if (lockState) {
            if (!redisTemplate.hasKey(codeInfo.codeCacheKey())){
                String sql = createSql(codeInfo);
                List<String> oneColData = DataBaseUtil.getOneColData(sql, codeInfo.getColName());
                saveDataDisCache(codeInfo, oneColData);
            }
            redisDistributedLock.releaseLock(codeInfo.lockKey());
        }else {
            throw new BizException("分布式锁获取失败");
        }
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/23 13:24
    * @remark 把code保存在redis的set里面
    */
    private void saveDataDisCache(CodeInfo codeInfo, List<String> dataList) {
        String cacheKey = codeInfo.codeCacheKey();
        for (String data: dataList) {
            redisTemplate.opsForSet().add(cacheKey, data);
        }
        if (dataList.size() > 0) {
            redisTemplate.expire(cacheKey, 10, TimeUnit.DAYS);
        }
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/23 13:25
    * @remark 生成查询数据的sql
    */
    public String createSql(CodeInfo codeInfo) {
        return codeInfo.allCodeSql();
    }

    @Override
    public Boolean saveCode(CodeInfo codeInfo, String newCode) {
        String cacheKey = codeInfo.codeCacheKey();
        if (!redisTemplate.hasKey(cacheKey)){
            cacheInitData(codeInfo);
        }
        Long addResult = redisTemplate.opsForSet().add(cacheKey, newCode);
        if (addResult == 1){
            return true;
        }else {
            return false;
        }
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/10 11:54
    * @remark 得到洗下一个编码
    */
    protected String getNextCode(CodeInfo codeInfo, NextCode nextCode, Integer retryCount){
        Integer count = 1;
        String cacheKey = codeInfo.codeCacheKey();
        while (true) {
            String code = nextCode.create();
            if (StringUtils.isNotBlank(nextCode.codePrefix())){
                code = nextCode.codePrefix() + code;
            }
            // 1添加成功 0添加失败
            Long addSuc = redisTemplate.opsForSet().add(cacheKey, code);
            if (addSuc == 1){
                return code;
            }
            count++;
            if(count > retryCount){
                log.error("生成编码超过系统的最大次数：【{}】", cacheKey);
                throw new BizException("生成编码超过系统的最大次数");
            }
        }
    }

}
