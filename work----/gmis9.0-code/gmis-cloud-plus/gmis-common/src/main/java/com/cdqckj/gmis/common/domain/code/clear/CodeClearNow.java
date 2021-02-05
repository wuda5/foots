package com.cdqckj.gmis.common.domain.code.clear;

import com.cdqckj.gmis.common.domain.code.CodeInfo;
import com.cdqckj.gmis.common.domain.code.CodeTypeEnum;
import com.cdqckj.gmis.common.domain.code.TempSaveCodeInfo;
import com.cdqckj.gmis.common.key.RedisCommonKey;
import com.cdqckj.gmis.common.utils.CacheKeyUtil;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.lock.DistributedLock;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/12/22 14:14
 * @remark: 请添加类说明
 */
public class CodeClearNow implements CodeClear {

    /** 缓存 **/
    private RedisTemplate redisTemplate;

    /** 锁 **/
    private DistributedLock redisDistributedLock;

    public CodeClearNow(RedisTemplate redisTemplate, DistributedLock redisDistributedLock) {
        this.redisTemplate = redisTemplate;
        this.redisDistributedLock = redisDistributedLock;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/22 14:14
    * @remark 清空redis的缓存的code
    */
    @Override
    public void clearCache(String beginId) {

        String key = CacheKeyUtil.createTenantKey(RedisCommonKey.CODE_NOT_LOST_CODE_KEY, beginId);
        String lockKey = key + ":lock";
        Boolean lock = redisDistributedLock.lock(lockKey);
        if (lock) {
            // 可能有多个code
            List<TempSaveCodeInfo> tempSaveList = redisTemplate.opsForList().range(key, 0, -1);
            for (TempSaveCodeInfo tempSave: tempSaveList){
                if (tempSave.getNewCode() == false){
                    continue;
                }
                CodeTypeEnum typeEnum = CodeTypeEnum.get(tempSave.getCodeType());
                CodeInfo codeInfo = new CodeInfo(typeEnum, tempSave.getTableName(), tempSave.getColName(), tempSave.getCodeGroupType());
                String cacheKey = codeInfo.codeCacheKey();
                redisTemplate.delete(cacheKey);
                redisTemplate.opsForSet().remove(codeInfo.codeCacheKey(), tempSave.getCode());
                // 自增编码 这里不光要丢弃code，还要将自增的清空-这里还有问题--还需要添加一个丢失code的队列
                if (typeEnum.getType() == CodeTypeEnum.INC_TYPE_ONE.getType()){
                    String nextIncKey = codeInfo.nextIncKey();
                    redisTemplate.delete(nextIncKey);
                }
            }
            redisTemplate.delete(key);
            redisDistributedLock.releaseLock(lockKey);
        }
    }
}
